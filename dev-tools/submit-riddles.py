import requests
import os
import json

from dotenv import load_dotenv


# CONFIGURATION
RIDDLES_DIR = "riddles_backup"

load_dotenv(os.path.join(os.path.dirname(__file__), ".env"))
ADDR = os.getenv("ADDR")
USERNAME = os.getenv("AUTHOR_USERNAME")
PASSWORD = os.getenv("AUTHOR_PASSWORD")


LOGIN_URL = "{}/auth/login".format(ADDR)
SUBMIT_URL = "{}/api/riddles".format(ADDR)


def get_token():
    resp = requests.post(LOGIN_URL, json={"username": USERNAME, "password": PASSWORD})
    resp.raise_for_status()
    data = resp.json()
    # Adjust if your API returns token in a different field
    token = data.get("token") or data.get("access_token")
    if not token:
        raise Exception("No token found in login response")
    return token

def submit_riddle(riddle, token):
    headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}
    resp = requests.post(SUBMIT_URL, headers=headers, json=riddle)
    resp.raise_for_status()
    return resp.json()

def main():
    print("Logging in...")
    token = get_token()
    print("Login successful. Submitting riddles...")
    for fname in os.listdir(RIDDLES_DIR):
        if fname.endswith(".json"):
            path = os.path.join(RIDDLES_DIR, fname)
            with open(path, "r", encoding="utf-8") as f:
                riddle = json.load(f)
            print(f"Submitting {fname}...")
            result = submit_riddle(riddle, token)
            print(f"Submitted {fname}: {result}")

if __name__ == "__main__":
    main()
