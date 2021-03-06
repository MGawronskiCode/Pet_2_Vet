package pl.petlovers.Pet2Vet.entities.appUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.AppUserWithThisLoginAlreadyExistException;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.ChangeRoleAccessForbiddenException;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.AppUserNotFoundException;

import java.util.List;

@Slf4j
@Service
public class AppUserService {

  private final AppUserRepository appUserRepository;

  @Autowired
  public AppUserService(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  public List<AppUser> getAll() {
    log.info("Fetching all users");

    return appUserRepository.findAll()
        .stream()
        .filter(appUser -> !appUser.isDeleted())
        .toList();
  }

  public AppUser create(AppUserDTO newUserData, String password) {
    log.info("Creating " + newUserData.toString());
    try {
//      todo checking, if user with this id already exists - if yes, allow to create new only by admin (creating a new with the same id deletes previous account, inform admin about it)

      return appUserRepository.save(newUserData.toAppUser(password));
    } catch (DataIntegrityViolationException appUserWithThisLoginAlreadyExistsException) {

      throw new AppUserWithThisLoginAlreadyExistException();
    }
  }

  public AppUser get(long id) {
    log.info("Fetching user with id = " + id);
    final AppUser appUser = appUserRepository.findById(id)
        .orElseThrow(() -> new AppUserNotFoundException(id));

    if (appUser.isDeleted()) {

      throw new AppUserNotFoundException(id);
    } else {

      return appUser;
    }
  }

  public AppUser update(long id, AppUserDTO userNewData) {
    AppUser oldUserData = get(id);
    log.info("Updating of " + oldUserData.toString() + " to " + userNewData.toString());
    oldUserData.modify(userNewData);

    return appUserRepository.save(oldUserData);
  }

  public AppUser update(long id, AppUserDTO userNewData, boolean adminUpdates) {
    AppUser userOldData = get(id);

    if (roleUnchanged(userNewData, userOldData) || adminUpdates) {
      log.info("Updating of " + userOldData + " to " + userNewData);
      userOldData.modify(userNewData);

      return appUserRepository.save(userOldData);
    } else {

      throw new ChangeRoleAccessForbiddenException();
    }

  }

  private boolean roleUnchanged(AppUserDTO userNewData, AppUser userOldData) {
    return userOldData.getRole() == userNewData.getRole();
  }

  public AppUser updatePassword(long userId, String password) {
    AppUser oldUserData = get(userId);
    log.info("Updating password of " + oldUserData.toString());
    oldUserData.modifyPassword(password);

    return appUserRepository.save(oldUserData);
  }

  public AppUser updateLogin(long userId, String login) {
    AppUser oldUserData = get(userId);
    log.info("Updating login of " + oldUserData.toString());
    oldUserData.modifyLogin(login);

    return appUserRepository.save(oldUserData);
  }

  public void delete(long id) {
    AppUser userFromDb = get(id);
    log.info("Deleting user with id = " + id);
    userFromDb.delete();

    appUserRepository.save(userFromDb);
  }
}
