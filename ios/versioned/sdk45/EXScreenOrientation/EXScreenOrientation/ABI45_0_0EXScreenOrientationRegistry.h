//  Copyright © 2019-present 650 Industries. All rights reserved.

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <ABI45_0_0ExpoModulesCore/ABI45_0_0ExpoModulesCore.h>
#import <ABI45_0_0ExpoModulesCore/ABI45_0_0EXSingletonModule.h>

NS_ASSUME_NONNULL_BEGIN

@protocol ABI45_0_0EXOrientationListener <NSObject>

- (void)screenOrientationDidChange:(UIInterfaceOrientation)orientation;

@end

@protocol ABI45_0_0EXScreenOrientationEventEmitter <NSObject>

- (void)unregisterModuleFromReceivingNotification:(id<ABI45_0_0EXOrientationListener>)module;
- (void)registerModuleToReceiveNotification:(id<ABI45_0_0EXOrientationListener>)module;

@end

@protocol ABI45_0_0EXScreenOrientationRegistry <NSObject>

- (UIInterfaceOrientation)currentOrientation;
- (UIInterfaceOrientationMask)currentOrientationMask;
- (UITraitCollection *)currentTrailCollection;

- (void)setMask:(UIInterfaceOrientationMask)mask forModule:(id)module;

- (void)moduleDidForeground:(id)module;
- (void)moduleDidBackground:(id)module;
- (void)moduleWillDeallocate:(id)module;

@end

@interface ABI45_0_0EXScreenOrientationRegistry : ABI45_0_0EXSingletonModule <UIApplicationDelegate, ABI45_0_0EXScreenOrientationEventEmitter, ABI45_0_0EXScreenOrientationRegistry>

- (void)updateCurrentScreenOrientation;

- (UIInterfaceOrientationMask)requiredOrientationMask;
- (void)traitCollectionDidChangeTo:(UITraitCollection *)traitCollection;

@end

NS_ASSUME_NONNULL_END
