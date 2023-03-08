# Multi-stage build
FROM python:3.10-alpine
LABEL org.opencontainers.image.authors="TeamB-PolytechNice"
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
WORKDIR /opt/app
COPY ./requirements.txt /tmp/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /tmp/requirements.txt
COPY ./app /opt/app
RUN chown -R dockeruser:dockergroup /opt/app
EXPOSE 9191
# Starting the service (shell form of ENTRYPOINT used for substitution)
USER dockeruser
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "9191"]
