from python:3.10-slim
workdir /app
copy requirements.txt .
run pip install -r requirements.txt
copy . .
cmd ["python", "app.py"]
