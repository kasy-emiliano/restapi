package mg.mbdspringboot.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageResponse {
    private String message;
    
    public MessageResponse() {}
    
    public MessageResponse(String message) {
        this.message = message;
    }
}
