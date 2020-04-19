package srsqdiApp;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import srsCommon.srsSiteLib;
import srsqdiApp.beans.QDIScoreCardBean;
import srsqdiApp.scorecard.QDIScoreCardLib;
import srsqdiApp.scorecard.QDIScoreCardUpdate;
import srsqdiApp.scorecard.QDIScoreCardValuesAbstract;
import srsrcApp.srsrcIXConstants;

import com.Utils.UserLib;
import com.login.DBConnection;
import com.login.DBProcedure;

public class srsqdiScoreCard extends HttpServlet 
{

	/** Method to receive get requests from the Web Server  **/
	public void doGet (HttpServletRequest req, HttpServletResponse res)
	{
			processRequest (req, res);
	}

	/** Method to receive and process Post requests from the Web Server  **/
	public void doPost (HttpServletRequest req, HttpServletResponse res)
	{
			processRequest (req, res);
	}

	/**  Handle the request **/
	private void processRequest (HttpServletRequest req, HttpServletResponse res)
	{
		String sScoringDk=null;
		int iScoringDk=-1;
		String sPageSource=null;
		int iPageSource=-1;
		String sHomeURL=null;
		String sScoreCardDk=null;
		int iScoreCardDk=-1;
        String evalType = null;

		boolean bResult=false;

		DBConnection dbConn=null;
		DBProcedure dbProc=null;

		UserLib currentUser=null;
		QDIScoreCardBean qdiScoreCard=null;
		QDIScoreCardUpdate qdiScoreCardUpdate=null;
        
        ResourceBundle qdiProps = ResourceBundle.getBundle("qdi");

		String eMsg=null;
        
        // clean out the session object
        HttpSession sess = req.getSession();
        sess.setAttribute(QDIScoreCardLib.REQATTRIB_QDISCORECARD, null);


		try{

			//Check if user logged in
			currentUser = new UserLib();
			if(!srsSiteLib.isUserLoggedIn(req, currentUser)){
				//res.sendRedirect(srsSiteLib.getServletRedirect(srsSiteLib.PAGE_HOME));
				RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher(srsSiteLib.getServletForwarding(srsSiteLib.PAGE_HOME));
				dispatcher.forward(req, res);
				return;
			}

			sPageSource = req.getParameter(srsSiteLib.PARAM_PAGE_SOURCE);
			if((sPageSource==null)||(sPageSource.length()==0)){
				iPageSource = srsSiteLib.PAGE_QDI_HOME;
			}else{
				try{
					iPageSource = Integer.valueOf(sPageSource).intValue();
				}catch (Exception ne){
					iPageSource = srsSiteLib.PAGE_QDI_HOME;
				}
			}
			sHomeURL = srsSiteLib.getServletRedirect(iPageSource);

			sScoreCardDk = req.getParameter(srsSiteLib.PARAM_SCORECARD_DK);
			if((sScoreCardDk==null)||(sScoreCardDk.length()==0)){
				iScoreCardDk=-1;
			}else{
				try{
					iScoreCardDk = Integer.valueOf(sScoreCardDk).intValue();
					if(iScoreCardDk<=0){
						iScoreCardDk = -1;
					}
				}catch (Exception ne){
						iScoreCardDk=-1;
				}
			}
			if(iScoreCardDk>0){
				sHomeURL = sHomeURL+"?"+srsSiteLib.PARAM_SCORECARD_DK+"="+sScoreCardDk;
			}

			sScoringDk = req.getParameter(srsSiteLib.PARAM_SCORING_DK);
			if((sScoringDk==null)||(sScoringDk.length()==0)){
				if(iScoreCardDk == -1) {
					eMsg = "No Scoring Sheet Id found.  Cannot Continue";
				}
			}
			try{
				iScoringDk = Integer.valueOf(sScoringDk).intValue();
				if(iScoringDk<=0){
					if(iScoreCardDk == -1) {
						eMsg = "Invalid Scoring Sheet Id["+sScoringDk+"] found.  Cannot Continue";
						iScoringDk = -1;
					}
				}
			}catch (Exception ne){
				if(iScoreCardDk == -1) {
					eMsg = "Non-numeric Scoring Sheet Id["+sScoringDk+"] found.  Cannot Continue";
					iScoringDk=-1;
				}
			}
			if((iScoringDk==-1) && (iScoreCardDk == -1)) {
				forwardJSP(true, null, sHomeURL, eMsg, req, res);
				return;
			}

            evalType = req.getParameter("supType");
            if (evalType != null) {
                if (evalType.startsWith("e"))
                    evalType = "E";
                else
                    evalType = "L";
            }
            
			//Open Connection for Updates and/or Retrievals
            if (qdiProps.getString("packagePrefix").indexOf("srsi_") != -1) {
                dbConn = new DBConnection(srsSiteLib.APPID, srsSiteLib.APPKEY);
            }
            else {
                dbConn = new DBConnection(srsrcIXConstants.APPID, srsrcIXConstants.APPKEY);
            }
			dbProc = new DBProcedure(dbConn);

			//Check whether Request was "POSTed" or whether initial "GET" request
			//Do not use object parsing of request unless "POSTed"
			if(req.getMethod().equalsIgnoreCase("POST")){
				qdiScoreCardUpdate = new QDIScoreCardUpdate();
				try{
					try{
						qdiScoreCardUpdate.parseRequest(req);
					}catch (Exception ue){
						closeDbResources(dbConn, dbProc);
						qdiScoreCardUpdate.close();
						eMsg = "[ScoreCard][Update]"+ue.toString();
						forwardJSP(true, null, sHomeURL, eMsg, req, res);
						return;
					}

					bResult = qdiScoreCardUpdate.updateScoreSheet (dbProc, currentUser.getUserID());
					if(!bResult){
						eMsg = qdiScoreCardUpdate.getLastError();
						closeDbResources(dbConn, dbProc);
						//forward to Edit page, with message and existing values
						forwardJSP(false, qdiScoreCardUpdate, sHomeURL, eMsg, req, res);
						return;
					}
					qdiScoreCardUpdate.close();
					qdiScoreCardUpdate = null;

				}catch (Exception ue){
					closeDbResources(dbConn, dbProc);
					eMsg = "[ScoreCard][Update]"+ue.toString();
					//forward to Edit page, with message and existing values
					forwardJSP(false, qdiScoreCardUpdate, sHomeURL, eMsg, req, res);
				}
			}

			qdiScoreCard = new QDIScoreCardBean(currentUser.getUserID());
			//String appID = currentUser.getCustomParameter(srsSiteLib.USERLIB_CUSTPARAM_APPID);
			bResult = qdiScoreCard.getRows(dbProc, iScoringDk, iScoreCardDk, 35, evalType/*, appID, qdiProps*/);

			closeDbResources(dbConn, dbProc);

			if(!bResult){
				eMsg = "[srsqdiScoreCard]"+qdiScoreCard.getLastError();
				forwardJSP(true, null, sHomeURL, eMsg, req, res);
				return;

			}else{
				//Successful Result
				forwardJSP(false, qdiScoreCard, sHomeURL, eMsg, req, res);
				return;
			}

		}catch (Exception e){
			//Examine proper exception handling.  May need some above, before forwarding
		}

	}

	/*---------------------------- Private Functions ----------------------------*/


	private void forwardJSP(boolean isErrPage, QDIScoreCardValuesAbstract qdiValues, String pHomeURL, String peMsg, HttpServletRequest preq, HttpServletResponse pres)
		throws ServletException, IOException
	{
		if(!((peMsg==null)||(peMsg.length()==0))){
			preq.setAttribute(QDIScoreCardLib.REQATTRIB_EMSG, peMsg);
		}
		if(!((pHomeURL==null)||(pHomeURL.length()==0))){
			preq.setAttribute(QDIScoreCardLib.REQATTRIB_HURL, pHomeURL);
		}
		if(isErrPage){
			RequestDispatcher dispatcher =
				getServletContext().getRequestDispatcher(QDIScoreCardLib.FWD_SCORECARD_ERR);
			dispatcher.forward(preq, pres);

		}else{
            // add the QDIScoreCardBean bean to the request object. Same bean is used for 
            // internal and external, but the display is different.
            HttpSession sess = preq.getSession();
			if(qdiValues!=null){
                // This creates TWO REFERENCES to the same Object, NOT 2 Objects
				preq.setAttribute(QDIScoreCardLib.REQATTRIB_QDISCORECARD, qdiValues); // for the HTML page
                sess.setAttribute(QDIScoreCardLib.REQATTRIB_QDISCORECARD, qdiValues); // for the PDF
			}
            String isExt = (String) preq.getAttribute("ext");
            if (isExt != null && isExt.equals("0")) 
            {   // view is internal from SPRR app
                RequestDispatcher dispatcher =
                    //getServletContext().getRequestDispatcher("/srsqdiScoreCardSprrIntDisp");
                    getServletContext().getRequestDispatcher(QDIScoreCardLib.FWD_SCORECARD_INT_DISP);
                dispatcher.forward(preq, pres);
            } 
            else if (isExt != null && isExt.equals("1")) 
            {   // view is EXTERNAL from SPRR app
                RequestDispatcher dispatcher =
                    //getServletContext().getRequestDispatcher("/srsqdiScoreCardSprrIntDisp");
                    getServletContext().getRequestDispatcher(QDIScoreCardLib.FWD_SCORECARD_EXT_DISP);
                dispatcher.forward(preq, pres);                    
            }else
            {   // view is internal from QDI app
                RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(QDIScoreCardLib.FWD_SCORECARD_DISP);
                dispatcher.forward(preq, pres);               
            }                        
		}

	}


	private void closeDbResources(DBConnection pdbConn, DBProcedure pdbProc)
	{
		try{
			pdbProc.reset();
			pdbProc.close();
			pdbConn.close();

		}catch (Exception e){
		}
	}

}
