---
title: '"Application has not been registered" error'
---

When developing an Expo or React Native app, it's not uncommon to run into an error that looks like:

```
Application "main" has not been registered.
```

or

```
Invariant Violation: "main" has not been registered.
```

Where "main" can be any string.

## What this error means

### An exception may be preventing your app from registering itself

The most common cause of this error is that there is an exception thrown in your application before it's able to register itself. When a React Native application loads, there are two steps:

1. Load the JavaScript code, and if everything is successful, then your application will be registered. If there is any exception when loading the bundle then execution will be aborted and it will never reach the part where your application is registered.
2. Run the registered application. If loading the code failed, then the application won't be registered and you will see the error that is the subject of this page.

If you're in this situation, the error message you're seeing is a [red herring](https://en.wikipedia.org/wiki/Red_herring), it's distracting you from the real error that led to the application not being registered.

Look at your logs prior to this error message to see what may have caused it. A frequent cause is multiple versions of a native module dependency that registers itself as a view &mdash; for example [this StackOverflow thread](https://stackoverflow.com/questions/67543844/invariant-violation-main-has-not-been-registered-while-running-react-native-a/67550379) where the poster has multiple versions of `react-native-safe-area-context` in their dependencies.

### Your app root component may not registered

Another possibility is that there is a mismatch between the `AppKey` being provided to [`AppRegistry.registerComponent`](https://reactnative.dev/docs/appregistry#registercomponent), and the `AppKey` being registered on the native iOS or Android side.

In managed projects, the default behavior is to use "main" as the `AppKey`. This is handled for your automatically, and as long as you don't change the `"main"` field in your **package.json** from the default value then this will just work. If you want to customize the app entry point, please refer to the [registerRootComponent](/versions/latest/sdk/register-root-component.md) API reference.

In projects with native code, you will see something like this in your **index.js** by default:

```js
import { registerRootComponent } from 'expo';
import App from './App';
registerRootComponent(App);
```

where `registerRootComponent` is implemented as:

```js
function registerRootComponent(component) {
  AppRegistry.registerComponent('main', () => component);
}
```

And on the native side, in **AppDelegate.m** you should see:

```objectivec
RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:@"main" initialProperties:nil];
```

and in **MainActivity.java**:

```java
@Override
protected String getMainComponentName() {
  return "main";
}
```

By default, "main" is consistently used throughout the project. If you're running into this error, it's likely that something has been changed and these values no longer coincide. Ensure that the names you are registering on the JavaScript side are the ones expected on the native side (if you're using Expo's `registerRootComponent` function, that would be "main").

## Other considerations

This error can also occur in a few other scenarios, but it's less predictable and the fixes would be more specific to your project. For example, some other cases are:

- You're connecting to the wrong project's local development server. Try closing out other Expo CLI or React Native community CLI processes (find them with `ps -A | grep "expo\|react-native"`).
- If this error is only occurring in your production app, then try running the app locally in production mode with `npx expo start --no-dev --minify` to find the source of the error.
