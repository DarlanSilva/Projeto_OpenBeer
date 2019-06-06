package br.com.wda.OpenBeerProject.Infra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Darlan Silva
 */
@Component
public class FileSaver {

    @Autowired
    private HttpServletRequest request;

    private static final String DIRETORIO_UPLOAD = "C:/product-picture/";

    public String write(String baseFolder, MultipartFile file) {
        try {
            //String realPath = request.getServletContext().getRealPath("/" + baseFolder);
            Path realPath   = Paths.get(DIRETORIO_UPLOAD);
            String path = realPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(path));
            return file.getOriginalFilename();
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
