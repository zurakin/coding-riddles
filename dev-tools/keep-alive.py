import os
import time
import requests
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

ADDR = os.getenv('ADDR')
N_MINUTES = 5  # Interval in minutes

if not ADDR:
    raise ValueError("ADDR must be set in the .env file")

HEALTH_URL = f"{ADDR.rstrip('/')}/api/health"

print(f"Starting keep-alive script. Pinging {HEALTH_URL} every {N_MINUTES} minutes.")

while True:
    try:
        response = requests.get(HEALTH_URL, timeout=10)
        print(f"[{time.strftime('%Y-%m-%d %H:%M:%S')}] Status: {response.status_code} - {response.text.strip()}")
    except Exception as e:
        print(f"[{time.strftime('%Y-%m-%d %H:%M:%S')}] Error: {e}")
    time.sleep(N_MINUTES * 60)
