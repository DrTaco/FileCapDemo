package nl.louis.filecapdemo.model;

public enum FileCapError {

    VIRUS_FOUND("There is a virus found in the uploaded file. The transfer has been cancelled."),
    NO_RECIPIENT_GIVEN("You did not specify any recipients for the transfer."),
    FILETYPE_NOT_ALLOW("The filetype is not allowed, either because of a forbidden MIME TYPE or forbidden file extension."),
    PASSWORD_MANDATORY_BUT_NOT_GIVEN("The administrator set the use of a password as mandatory, but none is given in the transfer options."),
    CANNOT_SEND_MAIL("The FileCap server was not able to send the email. Most commonly either the mail server was not configured correct, or the FileCap server is not authenticated to relay mail."),
    API_KEY_INVALID("The given API Key was invalid and cannot be used."),
    GENERAL_ERROR("There was an unknown error. Please check the server logs for the exception. If this error occurs you probably have a network / proxy issue.");

    private String message;

    FileCapError(String message) {
        this.message = message;
    }
}
