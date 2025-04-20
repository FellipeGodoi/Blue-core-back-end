package br.com.les.backend.les.src.service.product;

import br.com.les.backend.les.src.model.productModels.Socket;
import br.com.les.backend.les.src.repostory.productRepository.SocketProcessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SocketService {
    private final SocketProcessorRepository socketProcessorRepository;

    public Socket createSocket (Socket socket){
        if (socketProcessorRepository.findByModelo(socket.getModelo()).isEmpty())
        {
            throw new RuntimeException("Modelo ja existente");
        }
        return socketProcessorRepository.save(socket);
    }


    public Socket updateSocket(Socket socket, Long id){
        return socketProcessorRepository.findById(id)
                .map(existingSocket -> {
                    existingSocket.setModelo(socket.getModelo());
                    return socketProcessorRepository.save(existingSocket);
                }).orElseThrow(() -> new RuntimeException("Socket não encontrado"));
    }

    public List<Socket> getAllSockets(Socket socket){
        return socketProcessorRepository.findAll();
    }

    public void deleteSocket(Long id){
        socketProcessorRepository.findById(id).ifPresent(socketProcessorRepository::delete);
    }
}
