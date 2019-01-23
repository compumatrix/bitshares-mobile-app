//
//  MyPopviewManager.h
//  oplayer
//
//  Created by SYALON on 12/7/15.
//
//

#import <Foundation/Foundation.h>

typedef void (^Arg2CompletionBlock)(NSInteger buttonIndex, NSInteger cancelIndex);

@interface MyPopviewManager : NSObject<UIActionSheetDelegate>

+ (MyPopviewManager*)sharedMyPopviewManager;

/**
 *  显示 ActionSheet。
 */
- (void)showActionSheet:(UIViewController*)vc message:(NSString*)message cancel:(NSString*)cancelbuttonname items:(NSArray*)itemnamelist callback:(Arg2CompletionBlock)callback;

/**
 *  显示 ActionSheet （带红色按钮重点提示）。
 */
- (void)showActionSheet:(UIViewController*)vc message:(NSString*)message cancel:(NSString*)cancelbuttonname red:(NSString*)redbuttonname items:(NSArray*)itemnamelist callback:(Arg2CompletionBlock)callback;

@end
