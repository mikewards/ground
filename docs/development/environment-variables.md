# Environment Variables Reference

Complete reference for all environment variables used in the TBD platform.

## Required Variables

### Database

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection string | `jdbc:postgresql://localhost:5432/flow_api` |
| `DATABASE_USER` | Database username | `postgres` |
| `DATABASE_PASSWORD` | Database password | `your_secure_password` |

**Note**: `DATABASE_URL` is automatically set by Railway when you link a database.

### Security

| Variable | Description | How to Generate |
|----------|-------------|-----------------|
| `JWT_SECRET` | Secret key for JWT token signing (min 32 chars) | `openssl rand -hex 32` |
| `MASTER_ENCRYPTION_KEY` | AES-256 encryption key for wallet private keys (32 bytes hex) | `openssl rand -hex 32` |

### Environment

| Variable | Description | Values |
|----------|-------------|--------|
| `ENVIRONMENT` | Current environment | `development`, `sandbox`, `production` |

## Optional Variables

### Error Tracking

| Variable | Description | Example |
|----------|-------------|---------|
| `SENTRY_DSN` | Sentry error tracking URL | `https://xxx@sentry.io/xxx` |

### Logging

| Variable | Description | Values | Default |
|----------|-------------|--------|---------|
| `LOG_LEVEL` | Logging verbosity | `DEBUG`, `INFO`, `WARN`, `ERROR` | `INFO` |

### Blockchain RPC (Deprecated)

These are no longer required as RPC is handled internally:

| Variable | Description | Status |
|----------|-------------|--------|
| `ETH_SANDBOX_RPC_URL` | Sepolia testnet RPC URL | Deprecated |
| `ETH_PRODUCTION_RPC_URL` | Mainnet RPC URL | Deprecated |

## Environment-Specific Configuration

### Development

```bash
ENVIRONMENT=development
DATABASE_URL=jdbc:postgresql://localhost:5432/flow_api
DATABASE_USER=postgres
DATABASE_PASSWORD=local_password
JWT_SECRET=<generate>
MASTER_ENCRYPTION_KEY=<generate>
LOG_LEVEL=DEBUG
```

### Staging/Sandbox

```bash
ENVIRONMENT=sandbox
DATABASE_URL=<auto-set by Railway>
DATABASE_USER=postgres
DATABASE_PASSWORD=<from Railway database>
JWT_SECRET=<different from production>
MASTER_ENCRYPTION_KEY=<different from production>
LOG_LEVEL=INFO
```

### Production

```bash
ENVIRONMENT=production
DATABASE_URL=<auto-set by Railway>
DATABASE_USER=postgres
DATABASE_PASSWORD=<from Railway database>
JWT_SECRET=<strong, unique secret>
MASTER_ENCRYPTION_KEY=<strong, unique key>
SENTRY_DSN=<your Sentry DSN>
LOG_LEVEL=WARN
```

## Security Best Practices

1. **Never commit secrets**: Use environment variables, never hardcode
2. **Use different secrets per environment**: Never reuse production secrets in staging
3. **Rotate secrets regularly**: Especially if compromised
4. **Use strong secrets**: Minimum 32 characters for JWT_SECRET
5. **Limit access**: Only grant access to secrets to those who need them

## Generating Secrets

### JWT Secret

```bash
openssl rand -hex 32
```

### Encryption Key

```bash
openssl rand -hex 32
```

### Quick Generate Script

```bash
#!/bin/bash
echo "JWT_SECRET=$(openssl rand -hex 32)"
echo "MASTER_ENCRYPTION_KEY=$(openssl rand -hex 32)"
```

## Railway Configuration

Railway automatically sets `DATABASE_URL` when you link a database. You only need to set:
- `DATABASE_USER` (usually `postgres`)
- `DATABASE_PASSWORD` (from database settings)

## Local Development

Create a `.env` file in `flow-api/` directory:

```bash
ENVIRONMENT=development
DATABASE_URL=jdbc:postgresql://localhost:5432/flow_api
DATABASE_USER=postgres
DATABASE_PASSWORD=your_local_password
JWT_SECRET=<generate>
MASTER_ENCRYPTION_KEY=<generate>
```

The application reads from `.env` file in development mode.

## Related Documentation

- [Development Setup](./setup.md)
- [Railway Deployment](../deployment/railway.md)

