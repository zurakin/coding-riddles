import requests
import json
import os
from datetime import datetime
from dotenv import load_dotenv

load_dotenv(os.path.join(os.path.dirname(__file__), ".env"))
ADDR = os.getenv("ADDR")
API_BASE_URL = f"{ADDR}/api/riddles"

timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
OUTPUT_DIR = os.path.join("riddles_backup", timestamp)
os.makedirs(OUTPUT_DIR, exist_ok=True)

def remove_ids(obj):
    if isinstance(obj, dict):
        obj = {k: remove_ids(v) for k, v in obj.items() if k != "id"}
        return obj
    elif isinstance(obj, list):
        return [remove_ids(item) for item in obj]
    else:
        return obj

def main():
    print("Fetching riddles list...")
    resp = requests.get(API_BASE_URL)
    resp.raise_for_status()
    riddles = resp.json()

    for riddle in riddles:
        riddle_id = riddle["id"]
        print(f"Fetching riddle {riddle_id}...")
        riddle_resp = requests.get(f"{API_BASE_URL}/{riddle_id}")
        riddle_resp.raise_for_status()
        riddle_data = riddle_resp.json()
        # Remove 'id' from riddle and its test cases
        riddle_data_clean = remove_ids(riddle_data)
        # Save to file
        filename = os.path.join(OUTPUT_DIR, f"riddle_{riddle_id}.json")
        with open(filename, "w", encoding="utf-8") as f:
            json.dump(riddle_data_clean, f, ensure_ascii=False, indent=2)
        print(f"Saved riddle {riddle_id} to {filename}")

if __name__ == "__main__":
    main()
