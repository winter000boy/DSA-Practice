# Databricks Unity Catalog Column Masking with AWS Secrets Demo

## Overview

This project demonstrates **dynamic column-level masking** in Databricks Unity Catalog using AWS Secrets Manager for encryption key management. Different users see different versions of sensitive data (SSN) based on their access level:

- **Analyst User**: Sees raw SSN values
- **Restricted User**: Sees encrypted SSN values (encrypted via AWS secret key)
- **Other Users**: Sees fully masked values (`***-**-****`)

### Use Case
This solution is perfect for scenarios where you need:
- **Fine-grained data access control** based on user roles
- **Secure encryption** using external AWS managed secrets
- **Compliance** with data privacy regulations (GDPR, HIPAA, etc.)
- **Audit trails** for who sees what data

---

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│           Databricks Workspace                          │
│                                                         │
│  ┌─────────────────────────────────────────────────┐  │
│  │ Unity Catalog Service Credential                │  │
│  │ (Bridges Databricks → AWS)                      │  │
│  └─────────────┬───────────────────────────────────┘  │
│                │                                       │
└────────────────┼───────────────────────────────────────┘
                 │
                 │ AssumeRole with External ID
                 │
┌────────────────▼───────────────────────────────────────┐
│           AWS Account                                  │
│                                                        │
│  ┌──────────────────────────────────────────────────┐ │
│  │ IAM Role (databricks-privacera-uc-secrets-role)  │ │
│  │ - Trust Policy allows Databricks to assume       │ │
│  │ - Has permissions to access Secrets Manager      │ │
│  └──────────────────┬───────────────────────────────┘ │
│                     │                                  │
│  ┌──────────────────▼───────────────────────────────┐ │
│  │ AWS Secrets Manager                              │ │
│  │ Secret: db/privacera/privacera-secret            │ │
│  │ Value: encryption_key                            │ │
│  └──────────────────────────────────────────────────┘ │
│                                                        │
└────────────────────────────────────────────────────────┘
```

---

## Prerequisites

### Required Accounts & Access
- ✅ **AWS Account** with permissions to:
  - Create/modify IAM roles
  - Create/modify Secrets Manager secrets
  - View Account ID
- ✅ **Databricks Workspace** with:
  - Admin access (for Notebook 2)
  - Unity Catalog enabled
  - At least one existing catalog
- ✅ **2 Test Users** in your Databricks workspace (can be test email addresses)

### Required Information
Before you start, gather:

| Item | Where to Find | Example |
|------|---|---|
| AWS Account ID | AWS Console → Account settings | `123456789012` |
| AWS Region | Where your resources are | `us-east-2` |
| AWS Access Key ID | AWS IAM → Your User → Security Credentials | `AKIAIOSFODNN7EXAMPLE` |
| AWS Secret Access Key | AWS IAM → Your User → Security Credentials | `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY` |
| Databricks Environment | Workspace URL | Commercial/GovCloud |
| Unity Catalog Name | Databricks → Catalogs | `my-catalog` |
| Test User 1 Email | Databricks Admin → Users | `analyst@company.com` |
| Test User 2 Email | Databricks Admin → Users | `restricted@company.com` |

---

## Configuration Values Checklist

### Notebook 1: Environment Setup

**AWS Configuration** (you provide):
```python
SECRET_NAME = "db/privacera/privacera-secret"        # AWS secret path
SECRET_VALUE = "my-super-secret-key"                 # Encryption key (strong random string)
ROLE_NAME = "databricks-privacera-uc-secrets-role"   # IAM role name
AWS_REGION = "us-east-2"                              # Your AWS region
KMS_KEY_ID = ""                                        # Leave empty for default encryption
```

**Databricks Configuration** (you provide):
```python
SERVICE_CREDENTIAL_NAME = "dw-aws-secret-cred"        # UC service credential name
COMMENT = "Service credential for AWS Secrets Manager access"
```

**UC Master Role ARN** (select ONE based on your environment):
```python
# For Commercial AWS
UC_MASTER_ROLE_ARN = "arn:aws:iam::414351767826:role/unity-catalog-prod-UCMasterRole-14S5ZJVKOTYTL"

# For GovCloud
UC_MASTER_ROLE_ARN = "arn:aws-us-gov:iam::044793339203:role/unity-catalog-prod-UCMasterRole-1QRFA8SGY15OJ"

# For GovCloud DoD
UC_MASTER_ROLE_ARN = "arn:aws-us-gov:iam::170661010020:role/unity-catalog-prod-UCMasterRole-1DI6DL6ZP26AS"
```

**AWS Credentials** (choose ONE option):
```python
# Option A: Provide interactively (when prompted during notebook run)
DB_SECRET_SCOPE = None
DB_SECRET_AWS_ACCESS_KEY = None
DB_SECRET_AWS_SECRET_ACCESS_KEY = None
DB_SECRET_AWS_SESSION_TOKEN = None

# Option B: Store in Databricks Secrets first, then reference
DB_SECRET_SCOPE = "my-secret-scope"
DB_SECRET_AWS_ACCESS_KEY = "aws-access-key"
DB_SECRET_AWS_SECRET_ACCESS_KEY = "aws-secret-key"
DB_SECRET_AWS_SESSION_TOKEN = "aws-session-token"  # Optional
```

### Notebook 2: Admin Setup

**Must match Notebook 1 values**:
```python
CATALOG_NAME = "dw-example-catalog1"                  # Your Unity Catalog
SCHEMA_NAME = "default"                               # Schema in the catalog
SECRET_NAME = "db/privacera/privacera-secret"        # MUST MATCH Notebook 1
AWS_REGION = "us-east-2"                              # MUST MATCH Notebook 1
SERVICE_CREDENTIAL_NAME = "dw-aws-secret-cred"       # MUST MATCH Notebook 1
```

**New configurations**:
```python
SSN_TAG = "pii_ssn"                                   # Column tag name
USER_RAW_SSN = "analyst@company.com"                 # User who sees raw SSN
USER_ENCRYPTED_SSN = "restricted@company.com"        # User who sees encrypted SSN
```

### Notebook 3: User Demo

**Must match Notebooks 1 & 2**:
```python
CATALOG_NAME = "dw-example-catalog1"                  # MUST MATCH Notebook 2
SCHEMA_NAME = "default"                               # MUST MATCH Notebook 2
SERVICE_CREDENTIAL_NAME = "dw-aws-secret-cred"       # MUST MATCH Notebook 1
```

---

## Step-by-Step Setup Guide

### Step 1: Prepare AWS Resources

1. **Create AWS Access Credentials**
   - Go to AWS Console → IAM → Users → [Your User]
   - Click "Security Credentials" tab
   - Create Access Key (if you don't have one)
   - Copy: **Access Key ID** and **Secret Access Key**
   - Store these securely (you'll need them for Notebook 1)

2. **Determine Your Environment**
   - Check your Databricks workspace URL
   - If it contains `cloud.databricks.com` → **Commercial** (use first UC Master Role ARN)
   - If it contains `gov.cloud.databricks.com` → **GovCloud** (use second ARN)
   - Ask your admin if unsure

3. **Note Your AWS Region**
   - Where your Databricks cluster is located (e.g., `us-east-2`)
   - OR where you want to create AWS resources

### Step 2: Prepare Databricks Resources

1. **Verify Unity Catalog Exists**
   - Databricks Workspace → Data → Catalogs
   - If no catalog exists, create one or ask your admin
   - Note the catalog name (e.g., `dw-example-catalog1`)

2. **Get Test User Emails**
   - Databricks Workspace → Admin Console → Users
   - Find or invite 2 test users
   - Note their email addresses
   - Example: `analyst@company.com`, `restricted@company.com`

3. **Optional: Create Databricks Secret Scope for AWS Credentials**
   - If you want to store AWS credentials securely in Databricks
   - User Settings → Secret Scopes
   - Create scope, add AWS keys as secrets
   - This is recommended instead of entering credentials interactively

### Step 3: Run Notebook 1 - Environment Setup

1. **Open** `00_environment_setup.ipynb` in your Databricks workspace
2. **Update Configuration Section**:
   ```python
   SECRET_NAME = "db/your-company/encryption-key"
   SECRET_VALUE = "your-strong-random-encryption-key"  # min 32 chars
   ROLE_NAME = "databricks-uc-role"
   SERVICE_CREDENTIAL_NAME = "my-aws-credential"
   UC_MASTER_ROLE_ARN = "..."  # Select based on your environment
   ```
3. **Set AWS Credentials** (choose one):
   - Leave as `None` and enter interactively when prompted, OR
   - Store in Databricks secrets and reference them
4. **Run the notebook** from top to bottom
5. **Verify outputs**:
   - ✅ AWS Account ID printed
   - ✅ Role ARN printed
   - ✅ Secret created/updated
   - ✅ Service credential created
   - ✅ Trust policy updated

**What it creates**:
- AWS Secrets Manager secret with your encryption key
- AWS IAM role with proper trust policy
- Databricks Unity Catalog service credential

### Step 4: Run Notebook 2 - Admin Setup

⚠️ **IMPORTANT: You MUST be a Databricks Workspace Admin to run this notebook**

1. **Open** `01_admin_setup.ipynb` in your Databricks workspace
2. **Verify you're logged in as Admin** - check workspace admin status
3. **Update Configuration Section**:
   ```python
   CATALOG_NAME = "your-catalog-name"
   SCHEMA_NAME = "default"
   USER_RAW_SSN = "analyst@company.com"
   USER_ENCRYPTED_SSN = "restricted@company.com"
   # Keep others matching Notebook 1
   ```
4. **Run the notebook** from top to bottom
5. **Verify outputs**:
   - ✅ Admin permissions verified
   - ✅ Table created: `sensitive_customers`
   - ✅ Column tagged with PII
   - ✅ Encryption UDF created
   - ✅ Masking function created
   - ✅ Column mask applied
   - ✅ Encryption UDF tested successfully

**What it creates**:
- Table: `sensitive_customers` with sample SSN data
- Batch Python UDF: `encrypt_with_aws_secret` (uses AWS service credential)
- Masking function: `ssn_mask` (applies different masks per user)
- Column mask: Applied to SSN column

### Step 5: Run Notebook 3 - User Demo

Run this notebook as each test user to see different masking results.

1. **Run as User 1** (analyst user - raw SSN):
   - Open `02_user_demo.ipynb`
   - Ensure you're logged in as `analyst@company.com`
   - Run the notebook
   - **Expected**: SSN column shows raw values (e.g., `123-45-6789`)

2. **Run as User 2** (restricted user - encrypted SSN):
   - Log out or switch user to `restricted@company.com`
   - Open `02_user_demo.ipynb`
   - Run the notebook
   - **Expected**: SSN column shows encrypted values (e.g., `ENC:base64encodedstring`)

3. **Run as Other User** (fully masked):
   - Log in as any other user (not analyst or restricted)
   - Open `02_user_demo.ipynb`
   - Run the notebook
   - **Expected**: SSN column shows `***-**-****`

---

## Expected Results

After completing all steps, you should see:

### Sample Data
```
| id | name           | email               | ssn         |
|----|----------------|---------------------|-------------|
| 1  | Alice Smith    | alice@example.com   | [MASKED]    |
| 2  | Bob Johnson    | bob@example.com     | [MASKED]    |
| 3  | Carol White    | carol@example.com   | [MASKED]    |
```

### What Each User Sees

**Analyst User** (`analyst@company.com`):
```
| id | name           | email               | ssn         |
|----|----------------|---------------------|-------------|
| 1  | Alice Smith    | alice@example.com   | 123-45-6789 |
| 2  | Bob Johnson    | bob@example.com     | 987-65-4321 |
| 3  | Carol White    | carol@example.com   | 555-12-3456 |
```

**Restricted User** (`restricted@company.com`):
```
| id | name           | email               | ssn                    |
|----|----------------|---------------------|------------------------|
| 1  | Alice Smith    | alice@example.com   | ENC:encrypted_base64_1 |
| 2  | Bob Johnson    | bob@example.com     | ENC:encrypted_base64_2 |
| 3  | Carol White    | carol@example.com   | ENC:encrypted_base64_3 |
```

**Other Users**:
```
| id | name           | email               | ssn         |
|----|----------------|---------------------|-------------|
| 1  | Alice Smith    | alice@example.com   | ***-**-**** |
| 2  | Bob Johnson    | bob@example.com     | ***-**-**** |
| 3  | Carol White    | carol@example.com   | ***-**-**** |
```

---

## Troubleshooting

### Notebook 1 Issues

**Issue**: "InvalidClientTokenId" when creating boto3 session
- **Cause**: AWS Access Key ID or Secret Access Key is incorrect
- **Solution**: Verify credentials in AWS IAM Console, re-enter them

**Issue**: "AccessDenied" creating secret in Secrets Manager
- **Cause**: AWS user doesn't have `secretsmanager:CreateSecret` permission
- **Solution**: Ask AWS admin to add required IAM permissions

**Issue**: "No catalog found" in Notebook 2
- **Cause**: Specified catalog doesn't exist in Unity Catalog
- **Solution**: Create catalog first or verify catalog name is correct

### Notebook 2 Issues

**Issue**: "PermissionDenied" when running Notebook 2
- **Cause**: You're not a Databricks workspace admin
- **Solution**: Ask workspace admin to run this notebook instead

**Issue**: "MANAGE errors" when altering table
- **Cause**: You don't own the table or lack schema permissions
- **Solution**: Drop the table and run Notebook 2 again (you'll own the new table)

**Issue**: UDF fails with "Service credential not found"
- **Cause**: `SERVICE_CREDENTIAL_NAME` doesn't match Notebook 1 OR credential not created successfully
- **Solution**: Run Notebook 1 again to ensure credential is created

### Notebook 3 Issues

**Issue**: Different users see the same masking (not different per user)
- **Cause**: Column mask not applied correctly OR UDF logic issue
- **Solution**: Check that mask is applied: `DESCRIBE TABLE sensitive_customers`

**Issue**: Encrypted user sees `***-**-****` instead of encrypted values
- **Cause**: User email doesn't match `USER_ENCRYPTED_SSN` config exactly
- **Solution**: Verify exact email match (case-sensitive) in Notebook 2 config

**Issue**: "Cannot access AWS secret" error
- **Cause**: Service credential doesn't have AWS permissions
- **Solution**: Verify IAM role trust policy in Notebook 1 was updated with correct External ID

---

## Security Considerations

### ✅ Best Practices Implemented
- **External ID** in trust policy prevents confused deputy problem
- **Service Credential** used instead of hardcoded AWS keys
- **UDF implementation hidden** from non-owners
- **Column masking** applied at query time (no data stored masked)
- **AWS Secrets Manager** for centralized key management

### ⚠️ Additional Security Hardening

1. **Restrict IAM Role**
   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Effect": "Allow",
         "Action": "secretsmanager:GetSecretValue",
         "Resource": "arn:aws:secretsmanager:region:account:secret:db/privacera/privacera-secret*"
       }
     ]
   }
   ```

2. **Encrypt AWS Secret with KMS**
   - Set `KMS_KEY_ID` in Notebook 1 to your KMS key ARN

3. **Audit Trail**
   - Enable Databricks workspace audit logs
   - Enable AWS CloudTrail for secret access

4. **Rotation Schedule**
   - Regularly rotate AWS Access Keys
   - Rotate encryption key in Secrets Manager periodically

---

## File Structure

```
DBX/
├── README.md                      # This file
├── 00_environment_setup.ipynb     # Step 1: AWS + UC setup
├── 00_environment_setup.py        # (Companion Python file)
├── 01_admin_setup.ipynb           # Step 2: Create masked table & UDF
├── 01_admin_setup.py              # (Companion Python file)
├── 02_user_demo.ipynb             # Step 3: Demonstrate masking
└── 02_user_demo.py                # (Companion Python file)
```

---

## Key Concepts

### Unity Catalog Service Credentials
- Secure way to store AWS credentials in Databricks
- Enables Python UDFs to access external resources
- Uses cross-account IAM roles with trust policies

### CREDENTIALS Clause in UDFs
```python
CREATE FUNCTION my_func()
CREDENTIALS (`credential-name` DEFAULT)
AS ...
```
- `DEFAULT` means use provided credential
- Allows UDF to access AWS resources securely
- Available for Python/Scala/SQL functions

### Column Masking
- Applied at query time (transparent to users)
- Uses SQL functions to determine what to show
- Can use `current_user()` for user-based logic
- Non-owners of mask function can't see implementation

### XOR Encryption
The demo uses simple XOR encryption:
```python
encrypted = bytes([data[i] ^ key[i % len(key)] for i in range(len(data))])
```
- **Note**: XOR is NOT cryptographically secure for production
- For production, use proper encryption (AES-256, etc.)
- This demo is for illustration purposes only

---

## Next Steps & Extensions

### To Extend This Demo

1. **Add More Users & Roles**
   - Add more `USER_*` configurations
   - Extend masking function with additional conditions

2. **Use Real Encryption**
   - Replace XOR with `cryptography` library
   - Use proper key derivation (PBKDF2, etc.)

3. **Add Column Encryption**
   - Encrypt sensitive columns at rest in Delta tables
   - Use Databricks key management

4. **Audit Logging**
   - Log all accesses to masked data
   - Track who accessed what when

5. **Multiple Secrets**
   - Use different secrets for different columns
   - Implement key rotation

---

## Support & Troubleshooting

For issues:
1. Check the **Troubleshooting** section above
2. Review **Configuration Values Checklist** - ensure all values match
3. Check Databricks & AWS documentation
4. Review notebook output for error messages

---

## License

This demo is provided as-is for educational purposes.

---

## Change Log

| Date | Version | Changes |
|------|---------|---------|
| Jan 12, 2026 | 1.0 | Initial release |

