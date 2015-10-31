/**
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import <OCMock/OCMock.h>

#import "PFCommandRunningConstants.h"
#import "PFCommandURLRequestConstructor.h"
#import "PFHTTPRequest.h"
#import "PFInstallationIdentifierStore.h"
#import "PFRESTCommand.h"
#import "PFTestCase.h"

@interface CommandURLRequestConstructorTests : PFTestCase

@end

@implementation CommandURLRequestConstructorTests

///--------------------------------------
#pragma mark - Helpers
///--------------------------------------

- (id)mockedInstallationidentifierStoreProviderWithInstallationIdentifier:(NSString *)identifier {
    id providerMock = PFStrictProtocolMock(@protocol(PFInstallationIdentifierStoreProvider));
    id storeMock = PFStrictClassMock([PFInstallationIdentifierStore class]);
    OCMStub([providerMock installationIdentifierStore]).andReturn(storeMock);
    OCMStub([storeMock installationIdentifier]).andReturn(identifier);
    return providerMock;
}

///--------------------------------------
#pragma mark - Tests
///--------------------------------------

- (void)testConstructors {
    id providerMock = [self mockedInstallationidentifierStoreProviderWithInstallationIdentifier:nil];
    PFCommandURLRequestConstructor *constructor = [[PFCommandURLRequestConstructor alloc] initWithDataSource:providerMock];
    XCTAssertNotNil(constructor);
    XCTAssertEqual((id)constructor.dataSource, providerMock);

    constructor = [PFCommandURLRequestConstructor constructorWithDataSource:providerMock];
    XCTAssertNotNil(constructor);
    XCTAssertEqual((id)constructor.dataSource, providerMock);
}

- (void)testDataURLRequest {
    id providerMock = [self mockedInstallationidentifierStoreProviderWithInstallationIdentifier:@"installationId"];
    PFCommandURLRequestConstructor *constructor = [[PFCommandURLRequestConstructor alloc] initWithDataSource:providerMock];

    PFRESTCommand *command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                                     httpMethod:PFHTTPRequestMethodPOST
                                                     parameters:@{ @"a" : @"b" }
                                                   sessionToken:@"yarr"];
    command.additionalRequestHeaders = @{ @"CustomHeader" : @"CustomValue" };
    NSURLRequest *request = [constructor dataURLRequestForCommand:command];
    XCTAssertTrue([[request.URL absoluteString] containsString:@"/1/yolo"]);
    XCTAssertEqualObjects(request.allHTTPHeaderFields, (@{ PFCommandHeaderNameInstallationId : @"installationId",
                                                           PFCommandHeaderNameSessionToken : @"yarr",
                                                           PFHTTPRequestHeaderNameContentType : @"application/json; charset=utf-8",
                                                           @"CustomHeader" : @"CustomValue" }));
    XCTAssertEqualObjects(request.HTTPMethod, @"POST");
    XCTAssertNotNil(request.HTTPBody);
}

- (void)testDataURLRequestMethodOverride {
    id providerMock = [self mockedInstallationidentifierStoreProviderWithInstallationIdentifier:@"installationId"];
    PFCommandURLRequestConstructor *constructor = [[PFCommandURLRequestConstructor alloc] initWithDataSource:providerMock];

    PFRESTCommand *command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                                     httpMethod:PFHTTPRequestMethodGET
                                                     parameters:@{ @"a" : @"b" }
                                                   sessionToken:@"yarr"];
    NSURLRequest *request = [constructor dataURLRequestForCommand:command];
    XCTAssertEqualObjects(request.HTTPMethod, @"POST");

    command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                      httpMethod:PFHTTPRequestMethodHEAD
                                      parameters:@{ @"a" : @"b" }
                                    sessionToken:@"yarr"];
    request = [constructor dataURLRequestForCommand:command];
    XCTAssertEqualObjects(request.HTTPMethod, @"POST");

    command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                      httpMethod:PFHTTPRequestMethodGET
                                      parameters:@{ @"a" : @"b" }
                                    sessionToken:@"yarr"];
    request = [constructor dataURLRequestForCommand:command];
    XCTAssertEqualObjects(request.HTTPMethod, @"POST");

    command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                      httpMethod:PFHTTPRequestMethodGET
                                      parameters:nil
                                    sessionToken:@"yarr"];
    request = [constructor dataURLRequestForCommand:command];
    XCTAssertEqualObjects(request.HTTPMethod, @"GET");
}

- (void)testDataURLRequestBodyEncoding {
    id providerMock = [self mockedInstallationidentifierStoreProviderWithInstallationIdentifier:@"installationId"];
    PFCommandURLRequestConstructor *constructor = [[PFCommandURLRequestConstructor alloc] initWithDataSource:providerMock];

    PFRESTCommand *command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                                     httpMethod:PFHTTPRequestMethodPOST
                                                     parameters:@{ @"a" : @100500 }
                                                   sessionToken:@"yarr"];
    NSURLRequest *request = [constructor dataURLRequestForCommand:command];
    id json = [NSJSONSerialization JSONObjectWithData:request.HTTPBody options:0 error:nil];
    XCTAssertNotNil(json);
    XCTAssertEqualObjects(json, @{ @"a" : @100500 });
}

- (void)testFileUploadURLRequest {
    id providerMock = [self mockedInstallationidentifierStoreProviderWithInstallationIdentifier:@"installationId"];
    PFCommandURLRequestConstructor *constructor = [[PFCommandURLRequestConstructor alloc] initWithDataSource:providerMock];

    PFRESTCommand *command = [PFRESTCommand commandWithHTTPPath:@"yolo"
                                                     httpMethod:PFHTTPRequestMethodPOST
                                                     parameters:@{ @"a" : @100500 }
                                                   sessionToken:@"yarr"];
    NSURLRequest *request = [constructor fileUploadURLRequestForCommand:command
                                                        withContentType:@"boom"
                                                  contentSourceFilePath:@"/dev/null"];
    XCTAssertNotNil(request);
    XCTAssertEqualObjects(request.allHTTPHeaderFields[PFHTTPRequestHeaderNameContentType], @"boom");
}

- (void)testDefaultURLRequestHeaders {
    NSBundle *bundle = [NSBundle bundleForClass:[self class]];
    NSDictionary *headers = [PFCommandURLRequestConstructor defaultURLRequestHeadersForApplicationId:@"a"
                                                                                           clientKey:@"b"
                                                                                              bundle:bundle];
    XCTAssertNotNil(headers);
    XCTAssertEqualObjects(headers[PFCommandHeaderNameApplicationId], @"a");
    XCTAssertEqualObjects(headers[PFCommandHeaderNameClientKey], @"b");
    XCTAssertNotNil(headers[PFCommandHeaderNameClientVersion]);
    XCTAssertNotNil(headers[PFCommandHeaderNameOSVersion]);
    XCTAssertNotNil(headers[PFCommandHeaderNameAppBuildVersion]);
    XCTAssertNotNil(headers[PFCommandHeaderNameAppDisplayVersion]);
}

@end
