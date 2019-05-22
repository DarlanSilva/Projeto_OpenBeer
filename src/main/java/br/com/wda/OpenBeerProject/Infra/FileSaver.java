package br.com.wda.OpenBeerProject.Infra;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Darlan Silva
 */
@Component
public class FileSaver {

        @Autowired(required = true)
	private AmazonS3Client s3;

	public String write(String baseFolder, MultipartFile file) {
		try {
			s3.putObject("openbeer", file.getOriginalFilename(),
					file.getInputStream(), new ObjectMetadata());
			return "http://localhost:9444/s3/openbeer/"+file.getOriginalFilename()+"?noAuth=true";
		} catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}

	}

//    @Autowired
//    private HttpServletRequest request;
//
//    public String write(String baseFolder, MultipartFile file) {
//        try {
//            String realPath = request.getServletContext().getRealPath("/" + baseFolder);
//            String path = realPath + "/" + file.getOriginalFilename();
//            file.transferTo(new File(path));
//            return baseFolder + "/" + file.getOriginalFilename();
//        } catch (IllegalStateException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
