from fastapi import FastAPI
from transformers import pipeline

app = FastAPI()

pipe = pipeline(
    "text-generation",
    model="mistralai/Mistral-7B-Instruct-v0.1"
)

@app.post("/play")
def play(data: dict):
    prompt = data["prompt"]

    result = pipe(prompt, max_new_tokens=50)[0]["generated_text"]

    return {"response": result}