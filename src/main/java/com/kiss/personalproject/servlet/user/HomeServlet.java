package com.kiss.personalproject.servlet.user;

import com.kiss.personalproject.bean.zz.Account;
import com.kiss.personalproject.utils.AppUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = AppUtils.getLoginedUser(session);
        req.setAttribute("account", account);

        RequestDispatcher rd = req.getRequestDispatcher("/views/user/index.jsp");
        rd.forward(req, resp);
    }
}
