# TBD Platform Architecture

**Last Updated**: January 2025  
**Status**: Active Development  
**Environments**: Staging & Production Deployed

## Executive Overview

TBD is a DeFi API platform that provides developers with a unified REST API to access yield-generating opportunities across multiple DeFi protocols (Morpho and Aave). The platform abstracts protocol complexity, handles compliance, and enables developers to easily integrate cryptocurrency yield generation into their applications.

**Core Value**: A single, beautiful REST API that wraps multiple DeFi protocols, enabling developers to earn yield without managing protocol-specific integrations, smart contracts, or compliance requirements.

## System Architecture

```
┌─────────────────┐         ┌──────────────────┐         ┌──────────────┐
│   Cloudflare    │         │     Railway      │         │  PostgreSQL  │
│     Pages       │────────▶│   (Kotlin/Ktor)  │────────▶│   Database   │
│   (Frontend)    │         │    (Backend)     │         │              │
└─────────────────┘         └──────────────────┘         └──────────────┘
                                      │
                                      │
                    ┌─────────────────┴─────────────────┐
                    │                                     │
            ┌───────▼────────┐                  ┌────────▼────────┐
            │  Morpho Blue   │                  │   Aave V3       │
            │   GraphQL API  │                  │  GraphQL API     │
            └────────────────┘                  └─────────────────┘
```

## Technology Stack

### Frontend
- **HTML5, CSS3, JavaScript** - Vanilla web technologies
- **Cloudflare Pages** - Global CDN hosting
- **Design**: Inspired by Square/Stripe (2009 aesthetic)

### Backend
- **Kotlin 1.9.20** - Primary language
- **Ktor 2.3.5** - Lightweight web framework
- **PostgreSQL** - Relational database
- **Exposed** - Type-safe SQL framework
- **Web3j** - Ethereum blockchain integration
- **BouncyCastle** - Cryptographic operations

### Infrastructure
- **Railway.app** - Backend hosting (staging & production)
- **Cloudflare Pages** - Frontend hosting
- **PostgreSQL** - Managed database on Railway

## Core Components

### 1. Developer Portal & Documentation
- **Landing Page**: Modern, responsive design
- **API Reference**: Comprehensive documentation with interactive examples
- **Guides Section**: Integration patterns and use cases
- **SDK Playgrounds**: Interactive demos
- **Dashboard**: Application and key management

### 2. REST API Gateway
**Technology**: Kotlin, Ktor, PostgreSQL

**Features**:
- Authentication system (JWT tokens, Personal Access Tokens)
- Multi-application support with environment-specific credentials
- Developer dashboard for application and key management
- Request/response logging
- Rate limiting

### 3. Protocol Integration
**Working**:
- Morpho Blue API: Yield rate fetching, market listing
- Aave V3 API: Yield rate fetching, market listing
- Centralized `ProtocolService` with automatic protocol selection
- Graceful error handling with retry logic

**Architecture**: Single API surface that routes to appropriate protocol clients, automatically selecting best rates.

### 4. Security
- **JWT Authentication**: Secure token-based auth
- **AES-256-GCM Encryption**: Wallet private key encryption
- **Environment Separation**: Separate secrets per environment
- **CORS Protection**: Configurable CORS policies
- **Rate Limiting**: Per-endpoint configurable limits

### 5. Wallet & Key Management
- Segregated wallets per application
- Encrypted private key storage
- Gas wallet for transaction fees
- Token approval service for ERC20 tokens
- Web3 integration via web3j library

## Data Flow

### Yield Rate Request Flow

```
1. Client Request
   ↓
2. API Gateway (Ktor)
   ↓
3. Authentication Middleware
   ↓
4. Rate Limiting Middleware
   ↓
5. ProtocolService
   ├─→ MorphoClient (parallel)
   └─→ AaveClient (parallel)
   ↓
6. Aggregate Results
   ↓
7. Return Best Rates
   ↓
8. Response Logging
   ↓
9. Client Response
```

### Authentication Flow

```
1. Client: POST /v1/auth/authenticate
   ↓
2. Validate Credentials
   ↓
3. Generate JWT Token
   ↓
4. Return Token
   ↓
5. Client: Use Token in Authorization Header
   ↓
6. Middleware: Validate Token
   ↓
7. Extract User/Application Context
   ↓
8. Process Request
```

## Database Schema

### Core Tables
- `accounts` - User accounts
- `applications` - Developer applications
- `access_tokens` - API keys (PATs)
- `yield_accounts` - Yield account records
- `application_wallets` - Ethereum wallets per application
- `request_logs` - API request/response logging
- `transactions` - Transaction records
- `positions` - Yield position tracking

## Deployment Architecture

### Staging Environment
- **Branch**: `staging`
- **Backend**: `flow-platform-staging.up.railway.app`
- **Database**: Separate staging database
- **Purpose**: Testing and development

### Production Environment
- **Branch**: `main`
- **Backend**: `flow-platform-production.up.railway.app`
- **Database**: Separate production database
- **Purpose**: Live production traffic

## Security Architecture

### Authentication
- JWT tokens for temporary access
- Personal Access Tokens (PATs) for API access
- Token expiration and rotation

### Encryption
- AES-256-GCM for wallet private keys
- Separate encryption keys per environment
- Keys stored in environment variables

### Network Security
- HTTPS only (enforced by Railway/Cloudflare)
- CORS protection
- Rate limiting per endpoint

## Monitoring & Logging

### Request Logging
- All API requests logged to `request_logs` table
- 7-day retention period
- Includes: method, path, status, duration, IP, user agent

### Error Tracking
- Sentry integration for error tracking
- Automatic error reporting
- Stack traces and context

### Health Checks
- `GET /health` endpoint
- Database connectivity checks
- Service status monitoring

## Future Architecture Considerations

### Scalability
- Horizontal scaling via Railway
- Database connection pooling (HikariCP)
- Stateless API design

### Microservices (Future)
- Currently monolithic (single service)
- Could split into: Auth service, Protocol service, Wallet service
- Not needed until significant scale

### Caching (Future)
- Redis for rate limiting (currently in-memory)
- Response caching for market data (if needed)
- CDN caching for static assets (already via Cloudflare)

## Related Documentation

- [API Specification](../api/specification.md)
- [Development Setup](../development/setup.md)
- [Deployment Guide](../deployment/railway.md)

