from fastapi import FastAPI
from transformers import pipeline

app = FastAPI()

pipe = pipeline(
    "text2text-generation",
    model="google/flan-t5-small"
)

@app.post("/play")
def play(data: dict):
    prompt = data["prompt"]

    result = pipe(prompt, max_new_tokens=10, do_sample=False)[0]["generated_text"]

    return {"response": result}