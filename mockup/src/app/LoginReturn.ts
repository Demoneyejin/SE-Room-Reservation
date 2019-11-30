export interface LoginReturn {
	loginAttempts: number;
	loginSuccessful: boolean;
	sessionID: string
}

export interface UserInfo {
	email: string;
	password: string;
}