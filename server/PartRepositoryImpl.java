package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartRepositoryImpl implements PartRepository {
	private final List<Part> parts;

	public PartRepositoryImpl() {
		parts = new ArrayList<>();
	}

	@Override
	public Part add(Part part) {
		part.setCode(parts.size() + 1);

		parts.add(part);

		return part;
	}

	@Override
	public Optional<Part> findByCode(int code) {
		for (Part part : parts) {
			if (part.getCode() == code) {
				return Optional.of(part);
			}
		}

		return Optional.empty();
	}

	@Override
	public List<Part> findAll() {
		return parts;
	}
}
