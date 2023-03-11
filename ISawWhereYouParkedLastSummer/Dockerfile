FROM python:3.10-alpine
LABEL org.opencontainers.image.authors="TeamB-PolytechNice"
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
RUN mkdir -p /app
WORKDIR /app

COPY requirements.txt /
RUN pip install --no-cache-dir --upgrade --requirement /requirements.txt
RUN pip install 'httpx[cli]'
COPY ./app /app

EXPOSE 9191
ENTRYPOINT ["uvicorn","main:app","--host=0.0.0.0" , "--port", "9191"]
