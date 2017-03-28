package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class TaskDefinitions
{

    public class TestLoginTaskDefinition
    {
        public TestLoginTaskDefinition( UrlStepDefinition login, UrlStepDefinition credentials, UrlStepDefinition logout )
        {
            this.login = login;
            this.credentials = credentials;
            this.logout = logout;
        }

        public UrlStepDefinition login;
        public UrlStepDefinition credentials;
        public UrlStepDefinition logout;
    }

    public final TestLoginTaskDefinition Github = new TestLoginTaskDefinition(
      new UrlStepDefinition(
              "login",
              "login",
              "https://github.com/login",
              "<input.*id=\"login_field\" name=\"login\".*\\/>[.\\s\\S]*<input.*id=\"password\" name=\"password\".*\\/>[.\\s\\S]*<input.*name=\"commit\".*\\/>",
              UrlStepDefinition.UrlProvider.Static,
              UrlStepDefinition.ResultAction.Continue,
              UrlStepDefinition.ResultAction.TerminateTask
      ),
        new UrlStepDefinition(
                "credentials",
                "credentials",
                "javascript:document.getElementById('login_field').value='#username#';document.getElementById('password').value='#password#';document.getElementsByName('commit')[0].click();",
                "<button type=\"submit\" class=\"dropdown-item dropdown-signout\" data-ga-click=\"Header, sign out, icon:logout\">",
                UrlStepDefinition.UrlProvider.Credentials,
                UrlStepDefinition.ResultAction.Continue,
                UrlStepDefinition.ResultAction.GotoLast
        ),
        new UrlStepDefinition(
                "logout",
                "logout",
                "javascript:document.getElementsByClassName('dropdown-signout').click()",
                "<a class=\"text-bold site-header-link\" href=\"\\/login\".*<\\/a>",
                UrlStepDefinition.UrlProvider.Static,
                UrlStepDefinition.ResultAction.TerminateTask,
                UrlStepDefinition.ResultAction.TerminateTask
        )
    );
}