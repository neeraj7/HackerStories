# HackerStories

## Overview

This backend API has been developed using [HackerNewsAPI](https://github.com/HackerNews/API) for the users.

## Best Stories

Get the top 10 best stories ranked by score in the last 15 minutes

```javascript
[
    {
        "title": "Ninth Circuit rules NSA's bulk collection of Americans' call records was illegal",
        "url": "https://www.politico.com/news/2020/09/02/court-rules-nsa-phone-snooping-illegal-407727",
        "score": 1099,
        "time": 1599073943,
        "user": "AndrewBissell"
    },
    {
        "title": "Most-favorited Hacker News posts",
        "url": "https://observablehq.com/@tomlarkworthy/hacker-favourites-analysis",
        "score": 934,
        "time": 1599034054,
        "user": "tlarkworthy"
    },
    ...
    ,
    {
        "title": "After 48 years, Democrats endorse nuclear energy in platform",
        "url": "https://www.forbes.com/sites/robertbryce/2020/08/23/after-48-years-democrats-endorse-nuclear-energy-in-platform/#3c7687df5829",
        "score": 537,
        "time": 1598813544,
        "user": "elsewhen"
    }
]
```

## Past Stories

Get all the past best stories that served previously.


```javascript
[
    {
        "title": "Ninth Circuit rules NSA's bulk collection of Americans' call records was illegal",
        "url": "https://www.politico.com/news/2020/09/02/court-rules-nsa-phone-snooping-illegal-407727",
        "score": 1099,
        "time": 1599073943,
        "user": "AndrewBissell"
    },
    {
        "title": "Most-favorited Hacker News posts",
        "url": "https://observablehq.com/@tomlarkworthy/hacker-favourites-analysis",
        "score": 934,
        "time": 1599034054,
        "user": "tlarkworthy"
    },
    ...
    ,
    {
        "title": "After 48 years, Democrats endorse nuclear energy in platform",
        "url": "https://www.forbes.com/sites/robertbryce/2020/08/23/after-48-years-democrats-endorse-nuclear-energy-in-platform/#3c7687df5829",
        "score": 537,
        "time": 1598813544,
        "user": "elsewhen"
    }
]
```

## Comments

Get all the top 10 comments on a given story sorted by total number of child comments.


```javascript
[
    {
        "text": "The rationale they gave is that hate speech appears on these apps, because some of the microblogging sites that can be accessed via Fediverse have this kind of content. Based on this rationale, I look forward to Google Play removing Chrome, Firefox, and all other web browsers from the store as well.",
        "user": "humanistbot",
        "userProfileAge": 2
    },
    {
        "text": "&gt; Like hate speech is bad and it’s not censorship if it’s not mandated by the government.<p>To be fully accurate, this is absolutely <i>censorship</i>, but it&#x27;s not a violation of anyone&#x27;s First Amendment rights. People often conflate the two.<p>We can argue about whether or not Google should ban certain opinions on their platform, or where the line should be drawn, but it is arguable that hey have the legal right to do so.  And the Federal Government, just as inarguably, does <i>not</i> have this right.",
        "user": "thomascgalvin",
        "userProfileAge": 3
    },
    ...
    ,
    {
        "text": "It always starts like that.  People agree to very sensible things.  Like hate speech is bad and it’s not censorship if it’s not mandated by the government.  Eventually the definition of hate or whatever it is that’s offensive is very removed from the original meaning, and now we all bear the brunt of the sensible people who with best intentions wanted to make things better.",
        "user": "mc32",
        "userProfileAge": 9
    }
]
```
