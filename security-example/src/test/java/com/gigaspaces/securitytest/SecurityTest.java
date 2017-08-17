package com.gigaspaces.securitytest;

import com.gigaspaces.security.SecurityFactory;
import com.gigaspaces.security.SecurityManager;
import com.gigaspaces.security.authorities.SpaceAuthority;
import com.gigaspaces.security.directory.DirectoryManager;
import com.gigaspaces.security.directory.User;
import com.gigaspaces.security.directory.UserManager;
import com.gigaspaces.securitytest.space.CartService;
import org.junit.*;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.space.SecurityConfig;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.pu.container.ProcessingUnitContainer;
import org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainerProvider;

import java.util.Properties;

public class SecurityTest {

  public static final String SHOPPINGCART_SPACE_URL = "/./shoppingcart-space";
  public static final String LOOKUP_GROUPS = "testgroup";

  public static final String ADMIN_USERNAME = "admin";
  public static final String ADMIN_PASSWORD = "admin";

  public static final String USERNAME = "testuser";
  public static final String PASSWORD = "testuser";

  private static ProcessingUnitContainer container;

  @BeforeClass
  public static void initializeContainer() {
    // create container
    IntegratedProcessingUnitContainerProvider provider = new IntegratedProcessingUnitContainerProvider();
    ClusterInfo clusterInfo = new ClusterInfo();
    clusterInfo.setSchema("partitioned");
    clusterInfo.setNumberOfInstances(1);
    clusterInfo.setNumberOfBackups(0);
    clusterInfo.setInstanceId(1);
    provider.setClusterInfo(clusterInfo);
    container = provider.createContainer();
  }

  @Before
  public void setUp() {
    //create test user
    Properties securityProperties = new Properties();
    SecurityManager securityManager = SecurityFactory.createSecurityManager(securityProperties);
    DirectoryManager directoryManager = securityManager.createDirectoryManager(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
    UserManager userManager = directoryManager.getUserManager();
    userManager.createUser(new User(USERNAME, PASSWORD,
            new SpaceAuthority(SpaceAuthority.SpacePrivilege.WRITE),
            new SpaceAuthority(SpaceAuthority.SpacePrivilege.READ)
    ));
    directoryManager.close();
    securityManager.close();
  }

  @Test
  public void testSecurity() {
    UrlSpaceConfigurer urlConfigurer = new UrlSpaceConfigurer(SHOPPINGCART_SPACE_URL);
    urlConfigurer.lookupGroups(LOOKUP_GROUPS);
    urlConfigurer.securityConfig(new SecurityConfig(USERNAME, PASSWORD));
    GigaSpace gigaSpace = new GigaSpaceConfigurer(urlConfigurer).clustered(true).gigaSpace();
    CartService cartService = new CartService(gigaSpace);
    String cart101Json = "{'id':101,'user':'sudip','status':1,'shippingAddress':{'street':'beekman road','city':'kendall park','country':'usa','type':'residence'},'billingAddress':{'street':'5th avenue','city':'new york','country':'usa','billedParty':'gigaspaces'},'prize':{'name':'christmas surprise','amount':100},'items':[{'name':'book','count':1,'discounts':[{'name':'d1','amount':5},{'name':'d2','amount':2}]},{'name':'toy','count':4,'discounts':[{'name':'d1','amount':7},{'name':'d2','amount':4}]}]}";
    cartService.createCart(101, cart101Json);
    String cart102Json = "{'id':102,'user':'soham','shippingAddress':{'street':'beekman road','city':'kendall park','country':'usa','type':'residence'},'billingAddress':{'street':'5th avenue','city':'new york','country':'usa','billedParty':'gigaspaces'},'prize':{'name':'christmas surprise','amount':100},'items':[{'name':'book','count':1,'discounts':[{'name':'d1','amount':5},{'name':'d2','amount':2}]},{'name':'toy','count':4,'discounts':[{'name':'d1','amount':7},{'name':'d2','amount':4}]}]}";
    cartService.createCart(102, cart102Json);
    String cartRead = cartService.getCart(101);
    System.out.printf("Cart Read:\n%s\n", cartRead);
  }

  @After
  public void tearDown() throws Exception {
    // delete test user
    Properties securityProperties = new Properties();
    SecurityManager securityManager = SecurityFactory.createSecurityManager(securityProperties);
    DirectoryManager directoryManager = securityManager.createDirectoryManager(new User(ADMIN_USERNAME, ADMIN_PASSWORD));
    UserManager userManager = directoryManager.getUserManager();
    userManager.deleteUser(USERNAME);
    directoryManager.close();
    securityManager.close();
  }

  @AfterClass
  public static void cleanupContainer() {
    // close container
    container.close();
  }

}
