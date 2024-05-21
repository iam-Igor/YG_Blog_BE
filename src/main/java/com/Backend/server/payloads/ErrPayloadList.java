package com.Backend.server.payloads;

import java.util.List;

public record ErrPayloadList(

        String message,
        List<String> errorsList
) {


}
