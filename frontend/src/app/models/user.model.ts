
// src/app/shared/models/user.model.ts

export interface User {
    userId: number;
    username: string;
    email: string;
    name: string;
    bio?: string;
    profilePicture?: string;
    password?: string;
    newPassword?: string;
    newPasswordConfirmation?: string;
  }
  
  