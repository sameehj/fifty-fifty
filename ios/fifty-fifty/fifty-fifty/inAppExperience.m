//
//  ViewController.m
//  fifty-fifty
//
//  Created by personel on 10/30/15.
//  Copyright © 2015 personel. All rights reserved.
//

#import "inAppExperience.h"
#import "PayPalMobile.h"
#import "Parse.h"

@interface inAppExperience ()

@property PayPalConfiguration * _payPalConfiguration;
@property Boolean paid;
@property  int friendsOfFriendsofFriends;
@property  int friendsOfFriends;
@property  int friends;
@property  double credit;
@property  NSString * sourceId;
@property  NSString * objectId;
@property (weak, nonatomic) IBOutlet UITextView *refererId;
@property (weak, nonatomic) IBOutlet UITextView *creditText;
@property (weak, nonatomic) IBOutlet UITextView *FriendsText;
@property (weak, nonatomic) IBOutlet UITextView *friendsOfFriendsText;
@property (weak, nonatomic) IBOutlet UITextView *friendsOfFriendsOfFriendsText;
@property (weak, nonatomic) IBOutlet UIButton *registerBut;
@property (weak, nonatomic) IBOutlet UIButton *getBut;

@end

@implementation inAppExperience

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Do any additional setup after loading the view, typically from a nib.
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = true;
    
    
    [PayPalMobile preconnectWithEnvironment:PayPalEnvironmentNoNetwork];
    [self refreshData];
}

-(void)refreshData{
    PFQuery *query = [PFQuery queryWithClassName:@"User"];
    [query whereKey:@"Email" equalTo:@"kha@gmail.com"];
    
    PFObject *userObject = [query findObjects][0];
    
    int friendsOfFriendsofFriends = userObject[@"friendsOfFriendsofFriends"];
    self.paid = userObject[@"paid"];
        
    self.friendsOfFriendsofFriends=  userObject[@"friendsOfFriendsofFriends"];
    self.friendsOfFriends = userObject[@"friendsOfFriends"];
    self.friends = userObject[@"friends"];
//    self.credit = userObject[@"credit"];
    self.sourceId = userObject[@"sourceId"];
    self.objectId = userObject.objectId;
        
    if(self.paid) {
        NSString *refId =@"Referer Id: ";
        _refererId.text = [NSString stringWithFormat:@"%@%@", refId, self.objectId];
        [_registerBut setHidden:true];
        _getBut.enabled = YES;
        _creditText.text = [NSString stringWithFormat:@"%@%d", @"Credit: ",self.credit];
        _FriendsText.text = [NSString stringWithFormat:@"%@%d", @"Friends: ",self.friends];
        _friendsOfFriendsText.text = [NSString stringWithFormat:@"%@%d", @"Friends of friends: ",self.friendsOfFriendsofFriends];
        _friendsOfFriendsOfFriendsText.text = [NSString stringWithFormat:@"%@%d", @"Friends of friends of friends: ",self.friendsOfFriendsofFriends];
        
    }
    else{
        _getBut.enabled = NO;
        _refererId.text = @"Referer Id: Please register first";
//        Button registerBut = (Button) findViewById(R.id.register);
//        registerBut.setVisibility(View.VISIBLE);
//        Button getBut = (Button) findViewById(R.id.getMoney);
//        getBut.setEnabled(false);
//        TextView credit = (TextView) findViewById(R.id.credit);
//        credit.setText("Credit: " + 0);
//        TextView friends = (TextView) findViewById(R.id.rf);
//        friends.setText("Refered friends: "+0);
//        TextView friendsof = (TextView) findViewById(R.id.frf);
//        friendsof.setText("Friends of friends: "+0);
//        TextView friendsofof = (TextView) findViewById(R.id.frfrf);
//        friendsofof.setText("Friends of friends of friends: "+0);
//        ((FloatingActionButton) findViewById(R.id.fabRefresh)).setVisibility(View.INVISIBLE);
//        ((FloatingActionButton) findViewById(R.id.fabCopy)).setVisibility(View.INVISIBLE);
//        ((FloatingActionButton) findViewById(R.id.fabShare)).setVisibility(View.INVISIBLE);
        
    }
    

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)learnMoreWebpage:(id)sender {
    self.navigationController.navigationBarHidden = false;

    UIViewController *webViewController = [[UIViewController alloc] init];
    
    UIWebView *uiWebView = [[UIWebView alloc] initWithFrame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [uiWebView loadRequest:[NSURLRequest requestWithURL:
                            [NSURL URLWithString: @"https://www.google.com"]]];
    
    [webViewController.view addSubview: uiWebView];
    
    
    [self.navigationController pushViewController: webViewController animated:YES];
    
}

//
///**
// * For paypal
// */
//
//- (instancetype)initWithCoder:(NSCoder *)aDecoder {
//    self = [super initWithCoder:aDecoder];
//    if (self) {
//        self._payPalConfiguration = [[PayPalConfiguration alloc] init];
//        
//        // See PayPalConfiguration.h for details and default values.
//        
//        // Minimally, you will need to set three merchant information properties.
//        // These should be the same values that you provided to PayPal when you registered your app.
//        self._payPalConfiguration.merchantName = @"Ultramagnetic Omega Supreme";
//        self._payPalConfiguration.merchantPrivacyPolicyURL = [NSURL URLWithString:@"https://www.omega.supreme.example/privacy"];
//        self._payPalConfiguration.merchantUserAgreementURL = [NSURL URLWithString:@"https://www.omega.supreme.example/user_agreement"];
//        
//    }
//    return self;
//}

#pragma mark - PayPalFuturePaymentDelegate methods

//- (void)payPalFuturePaymentDidCancel:(PayPalFuturePaymentViewController *)futurePaymentViewController {
//    // User cancelled login. Dismiss the PayPalLoginViewController, breathe deeply.
//    [self dismissViewControllerAnimated:YES completion:nil];
//}
//
//- (void)payPalFuturePaymentViewController:(PayPalFuturePaymentViewController *)futurePaymentViewController
//                didAuthorizeFuturePayment:(NSDictionary *)futurePaymentAuthorization {
//    // The user has successfully logged into PayPal, and has consented to future payments.
//    
//    // Your code must now send the authorization response to your server.
//    [self sendAuthorizationToServer:futurePaymentAuthorization];
//    
//    // Be sure to dismiss the PayPalLoginViewController.
//    [self dismissViewControllerAnimated:YES completion:nil];
//}
//
//- (void)sendAuthorizationToServer:(NSDictionary *)authorization {
//    // Send the entire authorization reponse
//    NSData *consentJSONData = [NSJSONSerialization dataWithJSONObject:authorization
//                                                              options:0
//                                                                error:nil];
//    
//    // (Your network code here!)
//    //
//    // Send the authorization response to your server, where it can exchange the authorization code
//    // for OAuth access and refresh tokens.
//    //
//    // Your server must then store these tokens, so that your server code can execute payments
//    // for this user in the future.
//}



@end
