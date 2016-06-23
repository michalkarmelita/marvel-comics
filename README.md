# marvel-comics

## current limitations
- The App requires to implement offline mode. Currently API calls are cached only in memory to avoid unnecesary calls (ex. on screen rotation).
- Downloaded list of comics is limited to 100. It's retreived partially by 20 items in order to avoid long loding time. But normally I would implement infinite load more on scroll down event (something like in my training app https://github.com/michalkarmelita/twitter-hashtags-tracker/blob/master/app/src/main/java/com/michalkarmelita/hashtagtracker/model/tweets/TweetsDataProvider.java)

### tested on 
- Nexus 6p (Android N Beta)
