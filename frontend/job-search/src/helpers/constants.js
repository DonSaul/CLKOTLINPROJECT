//TODO


export const AUTH_TOKEN_NAME='jobSearchToken'

export const ROLES = {
    NO_ROLE:-1,
    CANDIDATE: 1,
    MANAGER: 2,
    ADMIN: 3,
  };


  export const getRoleString = (roleNumber) => {
    switch (roleNumber) {
      case ROLES.NO_ROLE:
        return 'No Role';
      case ROLES.CANDIDATE:
        return 'Candidate';
      case ROLES.MANAGER:
        return 'Manager';
      case ROLES.ADMIN:
        return 'Admin';
      default:
        return 'Unknown Role';
    }
  };

  export const NOTIFICATION_TYPES = {
    VACANCIES:1,
    INVITATIONS:2,
    MESSAGES:3
  }
  