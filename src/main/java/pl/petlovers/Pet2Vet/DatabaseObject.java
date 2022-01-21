package pl.petlovers.Pet2Vet;

public abstract class DatabaseObject{

  private boolean isDeleted;

  public boolean isDeleted () {
    return isDeleted;
  }

  public void delete() {
    isDeleted = true;
  }

  public void restore() {
    isDeleted = false;
  }

}
