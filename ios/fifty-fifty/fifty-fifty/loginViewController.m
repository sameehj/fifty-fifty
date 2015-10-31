//
//  ViewController.m
//  fifty-fifty
//
//  Created by personel on 10/30/15.
//  Copyright © 2015 personel. All rights reserved.
//

#import "loginViewController.h"
#import "PayPalMobile.h"

@interface loginViewController ()

@property PayPalConfiguration * _payPalConfiguration;

@end

@implementation loginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
//    self.navigationController.navigationBarHidden = true;
    // Do any additional setup after loading the view, typically from a nib.
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [PayPalMobile preconnectWithEnvironment:PayPalEnvironmentNoNetwork];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)learnMoreWebpage:(id)sender {
    
    UIViewController *webViewController = [[UIViewController alloc] init];
    
    UIWebView *uiWebView = [[UIWebView alloc] initWithFrame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [uiWebView loadRequest:[NSURLRequest requestWithURL:
                            [NSURL URLWithString: @"https://www.google.com"]]];
    
    [webViewController.view addSubview: uiWebView];

    
    [self.navigationController pushViewController: webViewController animated:YES];

}
- (IBAction)payPalLoginButton:(id)sender {
    PayPalFuturePaymentViewController *fpViewController;
    fpViewController = [[PayPalFuturePaymentViewController alloc] initWithConfiguration:self._payPalConfiguration
                                                                               delegate:self];
    
    // Present the PayPalFuturePaymentViewController
    [self presentViewController:fpViewController animated:YES completion:nil];

}

/**
 * For paypal
 */

- (instancetype)initWithCoder:(NSCoder *)aDecoder {
    self = [super initWithCoder:aDecoder];
    if (self) {
        self._payPalConfiguration = [[PayPalConfiguration alloc] init];
        
        // See PayPalConfiguration.h for details and default values.
        
        // Minimally, you will need to set three merchant information properties.
        // These should be the same values that you provided to PayPal when you registered your app.
        self._payPalConfiguration.merchantName = @"Ultramagnetic Omega Supreme";
        self._payPalConfiguration.merchantPrivacyPolicyURL = [NSURL URLWithString:@"https://www.omega.supreme.example/privacy"];
        self._payPalConfiguration.merchantUserAgreementURL = [NSURL URLWithString:@"https://www.omega.supreme.example/user_agreement"];
        
    }
    return self;
}

#pragma mark - PayPalFuturePaymentDelegate methods

- (void)payPalFuturePaymentDidCancel:(PayPalFuturePaymentViewController *)futurePaymentViewController {
    // User cancelled login. Dismiss the PayPalLoginViewController, breathe deeply.
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)payPalFuturePaymentViewController:(PayPalFuturePaymentViewController *)futurePaymentViewController
                didAuthorizeFuturePayment:(NSDictionary *)futurePaymentAuthorization {
    // The user has successfully logged into PayPal, and has consented to future payments.
    
    // Your code must now send the authorization response to your server.
    [self sendAuthorizationToServer:futurePaymentAuthorization];
    
    // Be sure to dismiss the PayPalLoginViewController.
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)sendAuthorizationToServer:(NSDictionary *)authorization {
    // Send the entire authorization reponse
    NSData *consentJSONData = [NSJSONSerialization dataWithJSONObject:authorization
                                                              options:0
                                                                error:nil];
    
    // (Your network code here!)
    //
    // Send the authorization response to your server, where it can exchange the authorization code
    // for OAuth access and refresh tokens.
    //
    // Your server must then store these tokens, so that your server code can execute payments
    // for this user in the future.
}



@end
