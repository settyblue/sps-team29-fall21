import tweepy
import csv

# # # # TWITTER AUTHENTICATIONS # # # #
ACCESS_TOKEN = "832748214-2ZyVO360YaAWtsRSnf1V03cNJtfWrK7YoxHzOo11"
ACCESS_TOKEN_SECRET = "Mo4BGTHJ56ft3tUEQh3eh7dkjkaC3td5o668pgUPy66ik"
CONSUMER_KEY = "0MY5mQroWGrJG1fFdgkkHSqOe"
CONSUMER_SECRET = "c8DfKZ0GBGBuZQjlgTBCTqDy9NmZafabOlJ8l5ahuyl9F7ixSu"

auth = tweepy.auth.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
auth.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)

api = tweepy.API(auth)

# Open/create a file to append data to
csvFile = open('result.csv', 'a')

# Use csv writer
csvWriter = csv.writer(csvFile)

for tweet in tweepy.Cursor(api.search,
                           q="google",
                           since="2021-06-28",
                           until="2021-07-05",
                           lang="en").items():
    # Write a row to the CSV file. I use encode UTF-8
    csvWriter.writerow([tweet.created_at, tweet.text.encode('utf-8')])
    print (tweet.created_at, tweet.text)
csvFile.close()
