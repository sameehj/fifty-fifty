//
//  ViewController.m
//  fifty-fifty
//
//  Created by personel on 10/30/15.
//  Copyright © 2015 personel. All rights reserved.
//

#import "loginViewController.h"

@interface loginViewController ()

@end

@implementation loginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
//    self.navigationController.navigationBarHidden = true;
    // Do any additional setup after loading the view, typically from a nib.
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


@end
