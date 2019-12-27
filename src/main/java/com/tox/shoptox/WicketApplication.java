package com.tox.shoptox;

import org.apache.log4j.Logger;
import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.page.SimplePageAuthorizationStrategy;
import org.apache.wicket.authroles.authentication.pages.SignOutPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;
import org.apache.wicket.settings.RequestCycleSettings;
import org.hibernate.StaleObjectStateException;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.golfpadgps.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{

	private static Logger logger = Logger.getLogger(WicketApplication.class);

	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public Session newSession(Request request, Response response)
	{
		return new ShoptoxSession(request);
	}

	@Override
	protected void init()
	{
		super.init();


		HibernateUtil.getSessionFactory();

		getResourceSettings().setThrowExceptionOnMissingResource(false);
		getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.REDIRECT_TO_RENDER);


		setRequestCycleProvider(new IRequestCycleProvider() {
			@Override
			public RequestCycle apply(RequestCycleContext requestCycleContext) {
				return new RequestCycle(requestCycleContext){
					@Override
					protected void onBeginRequest() {
						super.onBeginRequest();
						try {
							if (HibernateUtil.getSessionFactory().getCurrentSession().getTransaction() == null || !HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
								HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
							}
						}catch (StaleObjectStateException exception) {
							logger.error(exception);
						}
					}

					@Override
					protected void onEndRequest() {
						super.onEndRequest();
						try {
							if (HibernateUtil.getSessionFactory().getCurrentSession().getTransaction() != null && HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
								HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
							}
						}catch (StaleObjectStateException exception) {
							logger.error(exception);
						}finally {

							HibernateUtil.getSessionFactory().getCurrentSession().close();
						}
					}
				};
			}
		});

		// Install a simple page authorization strategy, that checks all pages
		// of type AuthenticatedWebPage.
		IAuthorizationStrategy authorizationStrategy = new SimplePageAuthorizationStrategy(
				AuthenticatedWebPage.class, SignInPage.class)
		{
			@Override
			protected boolean isAuthorized()
			{
				// check whether the user is logged on
				return (((ShoptoxSession)Session.get()).isSignedIn());
			}
		};
		getSecuritySettings().setAuthorizationStrategy(authorizationStrategy);


		mountPage("home", HomePage.class);
		mountPage("admin", SignInPage.class);
		mountPage("signOut", SignOutPage.class);
		mountPage("registration", RegistrationPage.class);
		mountPage("singOut", SignOutPage.class);
		mountPage("products", ProductsPage.class);
		mountPage("product", ProductPage.class);
		mountPage("product-add", ProductAddPage.class);
		mountPage("order", OrderPage.class);
		mountPage("orders", OrdersPage.class);
		mountPage("user", UserPage.class);
		mountPage("users", UsersPage.class);




	}

}
