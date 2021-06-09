import java.util.List;
import java.util.Optional;

public class PartRepositoryImpl {
	private List<Part> parts;

	public Part add(Part part){
		parts.add(part)
		return part
	}

	public Optional<Part> findByCode(int code) {
		for(Part part : parts) {
			if(part.getCode() == code){
				return Optional.of(part);
			}
		}

		return Optional.empty();
	}

	public List<Part> findAll(){
		return parts;
	}

	public List<Part> findAll(PartRepository repository){
		return repository.findAll();
	}
}
