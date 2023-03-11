FROM python:3.10-alpine
LABEL org.opencontainers.image.authors="TeamB-PolytechNice"
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
RUN mkdir -p /app
WORKDIR /app

COPY requirements.txt /
RUN pip install --requirement /requirements.txt

COPY ./app /app

EXPOSE 9191
CMD ["uvicorn", "main:app", "--host=0.0.0.0" , "--port", "9191"]
