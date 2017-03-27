package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class TaskDefinitions
{
    public class UrlStepDefinition
    {
        public String url;
        public String regex;

        public UrlStepDefinition( String url, String regex )
        {
            this.url = url;
            this.regex = regex;
        }
    }

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
      new UrlStepDefinition( "https://github.com/login", "<input.*id=\"login_field\" name=\"login\".*\\/>[.\\s\\S]*<input.*id=\"password\" name=\"password\".*\\/>[.\\s\\S]*<input.*name=\"commit\".*\\/>" ),
        new UrlStepDefinition( "javascript:document.getElementById('login_field').value='#username#';document.getElementById('password').value='#password#';document.getElementsByName('commit')[0].click();", "<button type=\"submit\" class=\"dropdown-item dropdown-signout\" data-ga-click=\"Header, sign out, icon:logout\">" ),
        new UrlStepDefinition( "javascript:document.getElementsByClassName('dropdown-signout').click()", "<a class=\"text-bold site-header-link\" href=\"\\/login\".*<\\/a>" )
    );
}