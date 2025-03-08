//package br.com.les.backend.les.src.application.dto.error;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(RecursoNaoEncontradoException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErroResponse handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
//        return new ErroResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
//    }
//
//    public static class ErroResponse {
//        private int status;
//        private String mensagem;
//        private LocalDateTime timestamp;
//
//        public ErroResponse(int status, String mensagem, LocalDateTime timestamp) {
//            this.status = status;
//            this.mensagem = mensagem;
//            this.timestamp = timestamp;
//        }
//
//        public int getStatus() { return status; }
//        public String getMensagem() { return mensagem; }
//        public LocalDateTime getTimestamp() { return timestamp; }
//    }
//
//}
