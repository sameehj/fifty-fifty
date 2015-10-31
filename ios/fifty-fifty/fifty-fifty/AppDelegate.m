//
//  AppDelegate.m
//  fifty-fifty
//
//  Created by personel on 10/30/15.
//  Copyright © 2015 personel. All rights reserved.
//

#import "AppDelegate.h"
#import "PayPalMobile.h"
#import "Parse/Parse.h"

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {

        [PayPalMobile initializeWithClientIdsForEnvironments:@{PayPalEnvironmentProduction : @"AWtJumJv_xlSGtmiIgQINvu1ZwDt3WouvgRMrJNx48kbONsieK3IGJyTbKceS7Wy98_1kK267tTU3S77",
                                                               PayPalEnvironmentSandbox : @"EFXqjhqiPJzvzmmAYrFwCMNPVpHCfD_1pLr04tbjEFLcKTZtmN3zzqq-8YO3oQWDc-XtozCwmUybGaxp"}];
    [Parse setApplicationId:@"Fmj1JQB1MPypBh7jz53Yj6NoHpBLDMkW2P5zDGjk"
                  clientKey:@"jrAg1S76N75fxvCNuHqoo0rF8dFF2iPUdSNsX93u"];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
