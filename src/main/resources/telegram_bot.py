import telegram
import sys

CHAT_ID = YOUR_CHAT_ID # enter the value without quotes
TOKEN = '<YOUR BOT TOKEN'


def send_message(message):
    bot = telegram.Bot(token=TOKEN)
    bot.sendMessage(chat_id=CHAT_ID, text=message)


send_message(" ".join(sys.argv[1:]))
