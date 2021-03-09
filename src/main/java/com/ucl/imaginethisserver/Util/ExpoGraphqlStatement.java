package com.ucl.imaginethisserver.Util;

public class ExpoGraphqlStatement {

    public static String createQueryAccountIDStatement(String accountName) {
        String queryAccountIDStatement = "{\"query\":\"query {account {" +
                "byName(accountName: \\\"$accountName\\\") " +
                "{id}}" +
                "}\"," +
                "\"variables\":{}}";
        return queryAccountIDStatement.replace("$accountName", accountName);
    }

    public static String createUserInvitationForAccount(String accountID, String email, String permissions) {
        String invitationStatement =
        "{\"query\":\"mutation {userInvitation {createUserInvitationForAccount(" +
                "accountID:  \\\"$accountID\\\", email: \\\"$email\\\", permissions: $permissions) {" +
                "id}}}\",\"variables\":{}}";
        return invitationStatement.replace("$accountID", accountID)
                .replace("$email", email)
                .replace("$permissions", permissions);
    }

    public static String createCancelInvitationStatement(String invitationID) {
    String cancelInvitationStatement =
        "{\"query\":\"mutation {userInvitation {deleteUserInvitation(" +
                "id: \\\"$invitationID\\\") {" +
                "id}}}\",\"variables\":{}}";
        return cancelInvitationStatement.replace("$invitationID", invitationID);
    }
}
