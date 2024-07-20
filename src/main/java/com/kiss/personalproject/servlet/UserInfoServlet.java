package com.kiss.personalproject.servlet;

import com.kiss.personalproject.bean.zz.Account;
import com.kiss.personalproject.bean.zz.AccountInfo;
import com.kiss.personalproject.dao.zz.AccountInfoDAO;
import com.kiss.personalproject.module.zz.UserInfoModule;
import com.kiss.personalproject.utils.AppUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(UserInfoServlet.class.getName());

    Account account;
    UserInfoModule userInfoModule;

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ghi log loại yêu cầu
        System.out.println("Received request of type: " + req.getMethod());

        HttpSession session = req.getSession();
        account = AppUtils.getLoginedUser(session);
        userInfoModule = new UserInfoModule();

        // Gọi phương thức service của HttpServlet để xử lý tiếp
        super.service(req, resp);
    }

    public UserInfoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        AccountInfo accountInfo = new AccountInfoDAO(request).getDocument(AccountInfo.COL_NAME_USER_ID_IN_ID, account.getUserId());
        log.info("accountInfo: " + accountInfo);
        request.setAttribute("accountInfo", accountInfo);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/admin/users-profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountInfo AccountInfo_req = userInfoModule.setParamToAccountInfo(request);

        AccountInfo accountInfo= new AccountInfoDAO(request).getDocument(AccountInfo.COL_NAME_USER_ID_IN_ID, AccountInfo_req.getUserId());
        log.info("accountInfo: " + accountInfo);


        doGet(request, response);
    }

}
