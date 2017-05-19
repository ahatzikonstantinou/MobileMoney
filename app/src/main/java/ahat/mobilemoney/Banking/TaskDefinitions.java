package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class TaskDefinitions
{

    public class TestLoginTaskDefinition
    {
        public TestLoginTaskDefinition( StepDefinition login, StepDefinition credentials, StepDefinition submit, StepDefinition logout )
        {
            this.login = login;
            this.credentials = credentials;
            this.submit = submit;
            this.logout = logout;
        }

        public StepDefinition login;
        public StepDefinition credentials;
        public StepDefinition submit;
        public StepDefinition logout;
    }

    public final TestLoginTaskDefinition Github = new TestLoginTaskDefinition(
      new StepDefinition(
              "login",
              "login",
              "https://github.com/login",
              ".*input class=\"form-control\" type=\"email\" name=\"login\".*input class=\"form-control\" type=\"password\" name=\"password\".*",
              StepDefinition.UrlProvider.Static,
              StepDefinition.ResultAction.Continue,
              StepDefinition.ResultAction.TerminateTask
      ),
        new StepDefinition(
                "credentials",
                "credentials",
                "javascript:document.getElementById('login_field').value='" + CredentialsUrlProvider.UsernamePlaceholder + "';document.getElementById('password').value='" + CredentialsUrlProvider.PasswordPlaceholder + "';document.getElementsByName('commit')[0].click();",
                ".*<input.*id=\"login_field\" name=\"login\".*\\/>[.\\s\\S]*<input.*id=\"password\" name=\"password\".*\\/>[.\\s\\S]*<input.*name=\"commit\".*\\/>.*",
                StepDefinition.UrlProvider.Credentials,
                StepDefinition.ResultAction.Continue,
                StepDefinition.ResultAction.GotoLast
        ),
        new StepDefinition(
                "submit",
                "submit",
                "javascript:document.getElementsByName('commit')[0].click();",
                ".*<button type=\"submit\" class=\"dropdown-item dropdown-signout\" data-ga-click=\"Header, sign out, icon:logout\">.*",
                StepDefinition.UrlProvider.Credentials,
                StepDefinition.ResultAction.Continue,
                StepDefinition.ResultAction.GotoLast
        ),
        new StepDefinition(
                "logout",
                "logout",
                "javascript:document.getElementsByClassName('dropdown-signout').click()",
                ".*<a class=\"text-bold site-header-link\" href=\"\\/login\".*<\\/a>.*",
                StepDefinition.UrlProvider.Static,
                StepDefinition.ResultAction.TerminateTask,
                StepDefinition.ResultAction.TerminateTask
        )
    );
}