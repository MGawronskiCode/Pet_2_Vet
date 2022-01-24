package pl.petlovers.Pet2Vet;

public interface Deletable {

  boolean isDeleted();

  void delete();

  void restore();

}
