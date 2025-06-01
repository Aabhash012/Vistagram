import json
from faker import Faker
from datetime import datetime, timedelta
import random

fake = Faker()

def generate_seed_data(num_posts=50):
    locations = ["Paris", "Tokyo", "New York", "Rome", "Sydney"]
    return [
        {
            "username": f"user_{fake.user_name()}",
            "caption": f"{fake.sentence()} #{random.choice(locations)}",
            "image_url": f"/images/{random.randint(1,50)}.jpg",  # Placeholder images
            "timestamp": (datetime.now() - timedelta(days=random.randint(1, 365)))
            .strftime("%Y-%m-%d %H:%M:%S"),
            "location": random.choice(locations)
        }
        for _ in range(num_posts)
    ]

# Generate and save
with open('seed-data.json', 'w') as f:
    json.dump(generate_seed_data(), f, indent=2)
print("Generated seed-data.json")