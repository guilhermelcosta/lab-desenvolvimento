package pucminas.listatarefas.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    /**
     * Interceptador que é executado antes de realizar uma requisição
     *
     * @param request  requisição
     * @param response resposta
     * @param handler  handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("========================================");
        log.info("Recebendo requisição");
        return true;
    }

    /**
     * Interceptador que é executado após de realizar uma requisição
     *
     * @param request  requisição
     * @param response resposta
     * @param handler  handler
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("========================================");
        log.info("Requisição finalizada");
    }

    /**
     * Interceptador que é executado após uma requisição ser completamente finalizada
     * Não vi necessidade de fazer nenhuma implementação específica até o momento
     *
     * @param request  requisição
     * @param response resposta
     * @param handler  handler
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
