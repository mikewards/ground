# Railway Deployment Guide

Complete guide for deploying the TBD API to Railway with separate staging and production environments.

## Overview

- **Production Service**: Deploys from `main` branch → `flow-platform-production.up.railway.app`
- **Staging Service**: Deploys from `staging` branch → `flow-platform-staging.up.railway.app`
- **Separate Databases**: Each environment has its own PostgreSQL database
- **Separate Environment Variables**: Each service has its own configuration

## Prerequisites

- Railway account (https://railway.app)
- GitHub repository connected to Railway
- PostgreSQL database (created automatically)

## Production Setup

### 1. Create Production Service

1. Go to Railway dashboard: https://railway.app
2. Create a new project (or use existing)
3. Click **"+ New"** → **"GitHub Repo"**
4. Select your repository
5. Railway will auto-detect the Dockerfile
6. Name the service: `flow-platform-production`

### 2. Configure Production Service

**Settings** → **Source**:
- Branch: `main`
- Root Directory: `flow-api`

**Variables** (Set these):
```
ENVIRONMENT=production
DATABASE_USER=postgres
DATABASE_PASSWORD=<from production database>
JWT_SECRET=<generate: openssl rand -hex 32>
MASTER_ENCRYPTION_KEY=<generate: openssl rand -hex 32>
```
- `DATABASE_URL` will be auto-set when you link the database

### 3. Add PostgreSQL Database

1. Click **"+ New"** → **"Database"** → **"Add PostgreSQL"**
2. Name it: `flow-db-production`
3. Link it to `flow-platform-production` service
4. Railway will auto-set `DATABASE_URL`

### 4. Generate Domain

1. Settings → **Generate Domain**
2. Copy the URL (e.g., `flow-platform-production.up.railway.app`)

## Staging Setup

### 1. Create Staging Service

1. In the same Railway project, click **"+ New"** → **"GitHub Repo"**
2. Select the **same repository**
3. Name the service: `flow-platform-staging`

### 2. Configure Staging Service

**Settings** → **Source**:
- Branch: `staging`
- Root Directory: `flow-api`

**Variables**:
```
ENVIRONMENT=sandbox
DATABASE_USER=postgres
DATABASE_PASSWORD=<from staging database>
JWT_SECRET=<different from production - generate new>
MASTER_ENCRYPTION_KEY=<different from production - generate new>
```

### 3. Add Staging Database

1. Click **"+ New"** → **"Database"** → **"Add PostgreSQL"**
2. Name it: `flow-db-staging`
3. Link it to `flow-platform-staging` service

### 4. Generate Staging Domain

1. Settings → **Generate Domain**
2. Copy the URL (e.g., `flow-platform-staging.up.railway.app`)

## Environment Variables

### Required Variables

| Variable | Description | How to Generate |
|----------|-------------|-----------------|
| `ENVIRONMENT` | `production` or `sandbox` | Set manually |
| `DATABASE_URL` | PostgreSQL connection string | Auto-set by Railway |
| `DATABASE_USER` | Database username | Usually `postgres` |
| `DATABASE_PASSWORD` | Database password | From Railway database settings |
| `JWT_SECRET` | JWT signing secret | `openssl rand -hex 32` |
| `MASTER_ENCRYPTION_KEY` | Wallet encryption key | `openssl rand -hex 32` |

### Optional Variables

| Variable | Description |
|----------|-------------|
| `SENTRY_DSN` | Sentry error tracking URL |
| `LOG_LEVEL` | `DEBUG`, `INFO`, `WARN`, `ERROR` |

## Database Setup

Railway automatically creates and manages PostgreSQL databases. The `DATABASE_URL` environment variable is automatically set when you link a database to a service.

### Manual Database Access

1. Go to your database service in Railway
2. Click **"Connect"** tab
3. Copy connection details if needed for external tools

## Networking & Domains

### Public Networking

Railway services are publicly accessible by default. Each service gets a unique domain:
- Production: `flow-platform-production.up.railway.app`
- Staging: `flow-platform-staging.up.railway.app`

### Custom Domains

1. Go to service **Settings** → **Networking**
2. Click **"Custom Domain"**
3. Add your domain and configure DNS

## Troubleshooting

### Service Won't Start

1. Check **Logs** tab for errors
2. Verify all required environment variables are set
3. Check database connection (verify `DATABASE_URL`)

### Database Connection Issues

1. Ensure database is linked to service
2. Verify `DATABASE_URL` is set (check Variables tab)
3. Check database is running (Railway dashboard)

### Environment Variable Issues

1. Variables are case-sensitive
2. No quotes needed around values
3. Restart service after adding variables

## Deployment Process

### Automatic Deployment

- **Production**: Deploys automatically on push to `main` branch
- **Staging**: Deploys automatically on push to `staging` branch

### Manual Deployment

1. Go to service in Railway dashboard
2. Click **"Deploy"** → **"Deploy Now"**
3. Select branch to deploy from

## Monitoring

### View Logs

1. Go to service in Railway dashboard
2. Click **"Logs"** tab
3. Real-time logs are displayed

### Metrics

Railway provides basic metrics:
- CPU usage
- Memory usage
- Network traffic

For detailed monitoring, integrate with Sentry or other monitoring tools.

## Best Practices

1. **Never commit secrets**: Use Railway environment variables
2. **Separate environments**: Keep staging and production completely separate
3. **Test in staging**: Always test changes in staging before production
4. **Monitor logs**: Check logs after deployments
5. **Backup databases**: Railway handles backups, but consider additional backups for production

## Related Documentation

- [Cloudflare Pages Deployment](./cloudflare.md) - Frontend deployment
- [Development Setup](../development/setup.md) - Local development
- [Environment Variables](../development/environment-variables.md) - Complete variable reference

