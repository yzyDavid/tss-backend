package tss.responses.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;

public class GetPhotoResponse {
    private final @Nls
    String status;
    @Nullable
    private final Resource resource;

    public GetPhotoResponse(String status, Resource resource) {
        this.status = status;
        this.resource = resource;
    }

    public String getStatus() {
        return status;
    }

    public Resource getResource() {
        return resource;
    }
}
