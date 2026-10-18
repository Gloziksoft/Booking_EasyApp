
# BookingApp — Password Reset and Registration Email Verification

## 2026-10-17 — Registration email verification implementation

- Added email verification token handling to the registration flow.
- After registration, a verification token is created and sent to the user's email.
- Verification links are valid for 24 hours.
- Users must verify their email before using the account.

## 2026-10-18 — Login and registration UI updates

- Redirected successful registration to the login page.
- Added success and error messages to the login page.
- Removed the registration success message from the registration page.
- Added local Bootstrap Icons support for password visibility toggles on login and registration pages.

## Existing password reset functionality

- Password reset already uses a separate token.
- Password reset tokens are valid for 15 minutes.
- A token is invalidated after a successful password reset.

## Result

Registration email verification and the existing password reset flow use separate tokens for different purposes.
