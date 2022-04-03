package business.businesswork.api.controller;

import business.businesswork.util.KakaoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.HashMap;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KakaoApi kakaoApi;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();

        String accessToken = kakaoApi.getAccessToken(code);

        logger.info("accessToken = "+accessToken);
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        if (userInfo.get("email") != null) {
            httpSession.setAttribute("userId", userInfo.get("email"));
            httpSession.setAttribute("accessToken", accessToken);
        }

        modelAndView.addObject("userId", userInfo.get("email"));
        modelAndView.setViewName("index");

        logger.info("-----=------=-----"+modelAndView);
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("access_token"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
