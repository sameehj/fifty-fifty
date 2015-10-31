//
//  ViewController.m
//  fifty-fifty
//
//  Created by personel on 10/30/15.
//  Copyright © 2015 personel. All rights reserved.
//

#import "ViewController.h"
#import "loginViewController.h"
#import <Security/Security.h>

#define SERVICE_NAME @"ANY_NAME_FOR_YOU"
#define GROUP_NAME @"YOUR_APP_ID.com.apps.shared" //GROUP NAME should start with application identifier.
 
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    

}

- (void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:true];
    // Do any additional setup after loading the view, typically from a nib.
    
    [NSThread sleepForTimeInterval:.2];
    
    loginViewController *monitorMenuViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"loginViewController"];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:monitorMenuViewController];

    [self presentViewController:navigationController animated:NO completion:nil];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
